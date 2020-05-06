package ru.vnevzorov.Shop.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.Role;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.service.user.AbstractUserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class SecurityController {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    AbstractUserService abstractUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);

        return "jsp/login.jsp";
    }

    @GetMapping("/registration")
    public ModelAndView registration(Model model) {
        log.info("GET: /registration");

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

        AbstractUser filledUser = new User();
        filledUser.setFirstName(firstName);
        filledUser.setLastName(lastName);
        filledUser.setBirthday(parseDate(birthday));
        filledUser.setLogin(username);
        filledUser.setEmail(email);

        if (abstractUserService.getByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("doesEmailRegistered", true);
            redirectAttributes.addFlashAttribute("newUser", filledUser);
            return new RedirectView("/registration");
        }

        if (abstractUserService.getByLogin(username) != null) {
            redirectAttributes.addFlashAttribute("doesUsernameExist", true);
            redirectAttributes.addFlashAttribute("newUser", filledUser);
            return new RedirectView("/registration");
        }

        if (!password.equals(passwordConf)) {
            redirectAttributes.addFlashAttribute("doesPasswordsMatch", false);
            redirectAttributes.addFlashAttribute("newUser", filledUser);
            return new RedirectView("/registration");
        }

        //TODO добавить подтверждение по почте

        filledUser.setPassword(passwordEncoder.encode(password));
        filledUser.setRole(Role.USER);
        abstractUserService.save(filledUser);

        return new RedirectView("/login");
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
