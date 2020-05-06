package ru.vnevzorov.Shop.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.repository.AbstractUserRepository;

import java.util.Optional;

@Service
public class AbstractUserService {

    @Autowired
    AbstractUserRepository abstractUserRepository;

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

}
