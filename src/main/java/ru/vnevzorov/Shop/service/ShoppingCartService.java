package ru.vnevzorov.Shop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.*;
import ru.vnevzorov.Shop.repository.ShoppingCartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    OrderedProductService orderedProductService;

    public ShoppingCart getById(String cartId) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(Long.parseLong(cartId));
        ShoppingCart cart;
        if (shoppingCartOptional.isPresent()) {
            cart = shoppingCartOptional.get();
        } else {
            cart = null;
        }
        return cart;
    }

    @Transactional
    public void addProduct(String productId) {
        Product product = productService.getProductById(productId);
        User user = userService.getUserByLogin("firstUser"); //FIXME

        if (user.getShoppingCart() == null) {
            log.info("user doesnt have active cart");
            createShoppingCart(user);
        }
        ShoppingCart shoppingCart = getByUser(user);
        shoppingCart.getOrderedProducts().add(new OrderedProduct(shoppingCart, product, 1));
        updateTotalPriceAndDiscount(shoppingCart);

        log.info("product: " + product + " added to cart id = " + shoppingCart.getId());
    }

    private void createShoppingCart(User user) {
        log.info("start creating new cart for user login=" + user.getLogin());
        ShoppingCart shoppingCart = shoppingCartRepository.save(new ShoppingCart(user));
        userService.setShoppingCartForUser(shoppingCart, user);
        //??????????????
        //userService.setShoppingCartForUser(shoppingCart, user);
    }

    public ShoppingCart getByUser(User user) {
        return shoppingCartRepository.findByUser(user);
    }

    private void updateTotalPriceAndDiscount(ShoppingCart cart) {
        log.info("updating total price and discount for cart id=" + cart.getId());

        List<OrderedProduct> orderedProducts = cart.getOrderedProducts();
        Double totalPrice = 0.0;
        Double totalPriceWithoutDiscount = 0.0;
        for (OrderedProduct orderedProduct : orderedProducts) {
            Product product = orderedProduct.getProduct();
            int quantity = orderedProduct.getQuantity();
            totalPriceWithoutDiscount += product.getPrice() * quantity;
            if (product.getDiscount().getType() == "%") {
                totalPrice += product.getPrice() * ((100.0 - product.getDiscount().getValue()) / 100.0) * quantity;
            } else {
                totalPrice += product.getPrice();
            }
        }
        Double totalDiscount = totalPriceWithoutDiscount - totalPrice;

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscount(totalDiscount);

        log.info("update finished");
    }

    @Transactional
    public void changeProductQuantity(String orderedProductId, int newQuantity) {
        User user = userService.getUserByLogin("firstUser"); //FIXME

        ShoppingCart cart = shoppingCartRepository.findByUser(user);
        OrderedProduct orderedProduct = orderedProductService.getByCartAndId(cart, orderedProductId);

        orderedProduct.setQuantity(newQuantity);
        updateTotalPriceAndDiscount(cart);
    }

    @Transactional
    public void deleteOrderedProduct(String orderedProductId) {
        User user = userService.getUserByLogin("firstUser");
        Long id = Long.parseLong(orderedProductId);
        List<OrderedProduct> orderedProducts = user.getShoppingCart().getOrderedProducts();
        orderedProducts.removeIf((OrderedProduct product) -> {
            if (product.getId() == id) {
                orderedProductService.delete(product);
                //product.setShoppingCart(null); убрал orphanRemoval
                return true;
            } else {
                return false;
            }
        });
        updateTotalPriceAndDiscount(user.getShoppingCart());
    }

    @Transactional
    public void deleteCart() {
        User user = userService.getUserByLogin("firstUser");

        List<OrderedProduct> orderedProducts = user.getShoppingCart().getOrderedProducts();
        orderedProducts.forEach(product -> product.setShoppingCart(null));
        shoppingCartRepository.delete(user.getShoppingCart());
        userService.deleteCartFromUser(user);
    }

    public Integer calculateItems() {
        String login = "firstUser"; //FIXME
        User user = userService.getUserByLogin(login);
        if (user.getShoppingCart() == null) {
            return 0;
        }
        List<OrderedProduct> orderedProducts = user.getShoppingCart().getOrderedProducts();

        return orderedProducts.size();
    }

    /*@Deprecated
    @Transactional
    public void addProduct(ShoppingCart cart, Product product) {
        log.info("adding product id=" + product.getId() + " to cart id=" + cart.getId());

        cart.getProducts().add(product);
        updateTotalPriceAndDiscount(cart);

    }
*/
}
