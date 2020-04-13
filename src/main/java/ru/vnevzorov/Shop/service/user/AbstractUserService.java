package ru.vnevzorov.Shop.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.repository.AbstractUserRepository;

@Service
public class AbstractUserService {

    @Autowired
    AbstractUserRepository abstractUserRepository;

    public void save(AbstractUser user) {
        abstractUserRepository.save(user);
    }

    @Cacheable("all_users")
    public Iterable<AbstractUser> getAll() {
        return abstractUserRepository.findAll();
    }

}
