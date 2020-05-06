package ru.vnevzorov.Shop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.user.User;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends AbstractUserRepository<User> {
    User findByLogin(String login);

    User findById(String id);

    User findByShoppingCart(ShoppingCart cart);

    User findByShoppingCart(Optional<ShoppingCart> cart);

}
