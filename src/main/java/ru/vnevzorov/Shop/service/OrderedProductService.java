package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.OrderedProduct;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.User;
import ru.vnevzorov.Shop.repository.OrderedProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderedProductService {

    @Autowired
    OrderedProductRepository orderedProductRepository;

    @Autowired
    UserService userService;

    public Optional<OrderedProduct> getById(String id) {
        return orderedProductRepository.findById(Long.parseLong(id));
    }

    public OrderedProduct getByCartAndId(ShoppingCart cart, String id) {
        return orderedProductRepository.findByShoppingCartAndId(cart, Long.parseLong(id));
    }

    public void delete(OrderedProduct product) {
        orderedProductRepository.delete(product);
    }

}
