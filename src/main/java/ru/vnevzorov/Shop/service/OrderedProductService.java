package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.OrderedProduct;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.repository.OrderedProductRepository;
import ru.vnevzorov.Shop.service.user.UserService;

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

    public OrderedProduct getByCartAndId(ShoppingCart cart, Long id) {
        return orderedProductRepository.findByShoppingCartAndId(cart, id);
    }

    public Iterable<OrderedProduct> getAll() {
        return orderedProductRepository.findAll();
    }

    public void save(OrderedProduct product) {
        orderedProductRepository.save(product);
    }

    public void delete(OrderedProduct product) {
        orderedProductRepository.delete(product);
    }

}
