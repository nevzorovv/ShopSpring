package ru.vnevzorov.Shop.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.exception.UserAlreadyExistException;
import ru.vnevzorov.Shop.model.VerificationToken;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.Role;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.repository.AbstractUserRepository;
import ru.vnevzorov.Shop.repository.VerificationTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class AbstractUserService {

    @Autowired
    AbstractUserRepository abstractUserRepository;

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void save(AbstractUser user) {
        abstractUserRepository.save(user);
    }

    @CachePut("all_users")
    public Iterable<AbstractUser> getAll() {
        return abstractUserRepository.findAll();
    }

    public AbstractUser getByLogin(String login) {
        return abstractUserRepository.findByLogin(login);
    }

    public AbstractUser getByEmail(String email) {
        return abstractUserRepository.findByEmail(email);
    }

    public void createVerificationToken(AbstractUser abstractUser, String token) {
        tokenRepository.save(new VerificationToken(token, abstractUser));
    }

    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void registerNewUserAccount(HttpServletRequest request) throws UserAlreadyExistException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthday = request.getParameter("birthday");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConf = request.getParameter("passwordConf");

        UserAlreadyExistException exception = new UserAlreadyExistException();
        boolean throwException = false;
        if (getByEmail(email) != null) {
            exception.setEmailAlreadyExists(true);
            throwException = true;
        }
        if (getByLogin(username) != null) {
            exception.setLoginAlreadyExists(true);
            throwException = true;
        }
        if (!password.equals(passwordConf)) {
            exception.setPasswordsDontMatch(true);
            throwException = true;
        }
        if (throwException) {
            throw exception;
        }

        AbstractUser newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setBirthday(parseDate(birthday));
        newUser.setLogin(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(Role.USER);
        save(newUser);
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
