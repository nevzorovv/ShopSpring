package ru.vnevzorov.Shop.service;

import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.User;

@Service
public class AccessService {

    public boolean test(AbstractUser abstractUser) {
        System.out.println("AccessService.test");
        return false;
    }

}
