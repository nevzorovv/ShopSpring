package ru.vnevzorov.Shop.security;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.repository.AbstractUserRepository;
import ru.vnevzorov.Shop.service.user.AbstractUserService;
import ru.vnevzorov.Shop.service.user.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService { //сервис, который отвечает за вытаскивание из базы

    @Autowired
    AbstractUserService abstractUserService;

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AbstractUser abstractUser = abstractUserService.getByLogin(login);
        if (abstractUser == null) {
            throw new UsernameNotFoundException("пользователь с таким логином не найден");
        }

        return new UserDetailsImpl(abstractUser);
    }

    public AbstractUser getAbstractUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            try {
                throw new NotFoundException("No Authentication was found");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        Object object = auth.getPrincipal();
        String login = "";

        if (object instanceof UserDetailsImpl) {
            login = ((UserDetailsImpl) object).getUsername();
        } else {
            login = object.toString();
        }

        AbstractUser abstractUser = abstractUserService.getByLogin(login);

        return abstractUser;
    }

    public User getUser() {
        return userService.getUserByLogin(getAbstractUser().getLogin());
    }
}
