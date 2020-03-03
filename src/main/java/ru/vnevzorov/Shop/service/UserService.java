package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.User;
import ru.vnevzorov.Shop.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void setShoppingCartForUser(ShoppingCart cart, User user) {
        user.setShoppingCart(cart);
    }
}
