package ru.vnevzorov.Shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.repository.AbstractUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService { //сервис, который отвечает за вытаскивание из базы

    @Autowired
    AbstractUserRepository abstractUserRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AbstractUser abstractUser = abstractUserRepository.findByLogin(login);
        if (abstractUser == null) {
            throw new UsernameNotFoundException("пользователь с таким логином не найден");
        }

        return new UserDetailsImpl(abstractUser);
    }
}
