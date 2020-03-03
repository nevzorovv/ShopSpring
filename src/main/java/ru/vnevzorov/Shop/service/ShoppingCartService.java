package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.Discount;
import ru.vnevzorov.Shop.model.Product;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.User;
import ru.vnevzorov.Shop.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserService userService;

    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.save(new ShoppingCart(user));
        //??????????????
        userService.setShoppingCartForUser(shoppingCart, user);
    }

    public ShoppingCart getByUser(User user) {
        return shoppingCartRepository.findByUser(user);
    }

    @Transactional
    public void addProduct(ShoppingCart cart, Product product) {
        cart.getProducts().add(product);
        updateTotalPriceAndDiscount(cart);
    }

    private void updateTotalPriceAndDiscount(ShoppingCart cart) {
        List<Product> products = cart.getProducts();
        Double totalPrice = 0.0;
        Double totalPriceWithoutDiscount = 0.0;
        for (Product product : products) {
            totalPriceWithoutDiscount += product.getPrice();
            if (product.getDiscount().getType() == "%") {
                totalPrice += product.getPrice() * ((100.0 - product.getDiscount().getValue()) / 100.0);
            } else {
                totalPrice += product.getPrice();
            }
        }
        Double totalDiscount = totalPriceWithoutDiscount - totalPrice;

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscount(totalDiscount);

    }

}
