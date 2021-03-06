package ru.vnevzorov.Shop.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.repository.UserRepository;
import ru.vnevzorov.Shop.service.ShoppingCartService;

/*import ru.vnevzorov.Shop.model.User;*/

@Service
public class UserService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Cacheable("users")
    public User getUserByLoginCache(String login) {
        return userRepository.findByLogin(login);
    }

    @CachePut("users")
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void setShoppingCartForUser(ShoppingCart cart, User user) {
        log.info("setting cart id=" + cart.getId() + " for user id=" + user.getId());
        user.setShoppingCart(cart);
    }

    public User getByShoppingCart(ShoppingCart cart) {
        return userRepository.findByShoppingCart(cart);
    }

    public User getByShoppingCartId(String cartId) {
        return userRepository.findByShoppingCart(shoppingCartService.getById(cartId));
    }

    @Transactional
    public void deleteCartFromUser(User user) {
        user.setShoppingCart(null);
    }
}
