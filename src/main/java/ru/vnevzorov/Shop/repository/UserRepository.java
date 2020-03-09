package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.User;

import javax.persistence.Id;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);

    User findById(String id);

    User findByShoppingCart(ShoppingCart cart);

    User findByShoppingCart(Optional<ShoppingCart> cart);
}
