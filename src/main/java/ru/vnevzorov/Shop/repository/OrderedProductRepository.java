package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vnevzorov.Shop.model.OrderedProduct;
import ru.vnevzorov.Shop.model.ShoppingCart;

@Repository
public interface OrderedProductRepository extends CrudRepository<OrderedProduct, Long> {

    OrderedProduct findByShoppingCartAndId(ShoppingCart cart, Long id);

}
