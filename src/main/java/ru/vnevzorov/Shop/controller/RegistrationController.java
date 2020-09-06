package ru.vnevzorov.Shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;
import ru.vnevzorov.Shop.ApplicationEvent.OnRegistrationCompleteEvent;
import ru.vnevzorov.Shop.model.VerificationToken;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.Role;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.rest.dto.user.AbstractUserRestDTO;
import ru.vnevzorov.Shop.service.email.EmailService;
import ru.vnevzorov.Shop.service.user.AbstractUserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Locale;

@Controller
public class RegistrationController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    AbstractUserService abstractUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @GetMapping("/registration")
    public ModelAndView registration(Model model) {
        log.info("GET: /registration");

        if (model.getAttribute("newUser") == null) {
            model.addAttribute("newUser", new User());
        }
        return new ModelAndView("jsp/registration.jsp");
    }

    //registration by admin user
    @GetMapping("/register_new_user")
    public ModelAndView registerNewUser(Model model) {
        log.info("GET: /register_new_user");

        if (model.getAttribute("newUser") == null) {
            model.addAttribute("newUser", new User());
        }

        return new ModelAndView("jsp/registration.jsp");
    }

    @PostMapping("/registration")
    public RedirectView registration(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthday = request.getParameter("birthday");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConf = request.getParameter("passwordConf");
        String role = request.getParameter("role");

        AbstractUser newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setBirthday(parseDate(birthday));
        newUser.setLogin(username);
        newUser.setEmail(email);

        if (abstractUserService.getByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("doesEmailRegistered", true);
            redirectAttributes.addFlashAttribute("newUser", newUser);
            return new RedirectView("/registration");
        }

        if (abstractUserService.getByLogin(username) != null) {
            redirectAttributes.addFlashAttribute("doesUsernameExist", true);
            redirectAttributes.addFlashAttribute("newUser", newUser);
            return new RedirectView("/registration");
        }

        if (!password.equals(passwordConf)) {
            redirectAttributes.addFlashAttribute("doesPasswordsMatch", false);
            redirectAttributes.addFlashAttribute("newUser", newUser);
            return new RedirectView("/registration");
        }

        newUser.setPassword(passwordEncoder.encode(password));
        if (role == null) {
            newUser.setRole(Role.USER);
        } else {
            newUser.setRole(Role.valueOf(role.toUpperCase()));
            newUser.setPasswordChanged(false);
        }

        abstractUserService.save(newUser);

        try {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(request.getContextPath(), request.getLocale(), abstractUserService.getByLogin(username)));
        } catch (RuntimeException e) {
            log.error("There is an exception in the logic executed after the publishing of the event");
        }
        return new RedirectView("/login");
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = abstractUserService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/badUser?lang=" + locale.getLanguage();
        }

        AbstractUser abstractUser = verificationToken.getAbstractUser();
        if ((verificationToken.getExpiryDate().isBefore(LocalDate.now()))) {
            String message = messages.getMessage("auth.message.expired", null, locale);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/badUser?lang=" + locale.getLanguage();
        }

        abstractUser.setEnabled(true);
        abstractUserService.save(abstractUser);
        return "redirect:/login?regsucc&lang=" + request.getLocale().getLanguage();
    }

    @GetMapping("/badUser")
    public ModelAndView registrationError(Model model, @RequestParam("lang") String lang) {
        log.info("GET: /badUser");
        ModelAndView modelAndView = new ModelAndView("jsp/badUser.jsp");
        modelAndView.addObject("message", model.getAttribute("message"));

        return modelAndView;
    }

    @GetMapping("/password_change")
    public ModelAndView passwordChange(Model model) {
        log.info("GET: /password_change");

        return new ModelAndView("jsp/password_change.jsp");
    }

    @PostMapping("/password_change")
    public RedirectView passwordChange(HttpServletRequest request, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String passwordConf = request.getParameter("passwordConf");

        if (abstractUserService.checkCredentials(login, password)) {
            if (newPassword.equals(passwordConf)) {
                String passwordEncoded = passwordEncoder.encode(newPassword);

                AbstractUserRestDTO updatedAbstractUser = new AbstractUserRestDTO();
                updatedAbstractUser.setId(abstractUserService.getByLogin(login).getId());
                updatedAbstractUser.setLogin(login);
                updatedAbstractUser.setPassword(passwordEncoded);

                WebClient.Builder client = WebClient.builder();
                AbstractUserRestDTO mono = client.build()
                        .put()
                        .uri("http://localhost:8080/api/users/passwordUpdate/" + updatedAbstractUser.getId())
                        .body(Mono.just(updatedAbstractUser), AbstractUserRestDTO.class)
                        .retrieve()
                        .bodyToMono(AbstractUserRestDTO.class)
                        .block();

            } else {
                redirectAttributes.addFlashAttribute("passwordsMismatch", messages.getMessage("message.passwordsMismatch", null, request.getLocale()));
                return new RedirectView("/password_change");
            }
        } else {
            redirectAttributes.addFlashAttribute("wrong_credentials", messages.getMessage("message.badCredentials", null, request.getLocale()));
            return new RedirectView("/password_change");
        }

        return new RedirectView("/login?passSucChanged&lang=" + request.getLocale().getLanguage());
    }

    private LocalDate parseDate(String date) {
        String[] dateParts = date.split("-");
        if (dateParts[1].charAt(0) == '0') {
            dateParts[1] = dateParts[1].substring(1, 2);
        }
        if (dateParts[2].charAt(0) == '0') {
            dateParts[2] = dateParts[2].substring(1, 2);
        }
        return LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
    }
}
