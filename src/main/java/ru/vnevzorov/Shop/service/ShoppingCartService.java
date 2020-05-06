package ru.vnevzorov.Shop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.WrongClassException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.aspect.annotation.LogResult;
import ru.vnevzorov.Shop.model.OrderedProduct;
import ru.vnevzorov.Shop.model.Product;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.Role;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.repository.ShoppingCartRepository;
import ru.vnevzorov.Shop.security.UserDetailsImpl;
import ru.vnevzorov.Shop.security.UserDetailsServiceImpl;
import ru.vnevzorov.Shop.service.user.UserService;

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

    @Autowired
    UserDetailsServiceImpl userDetailsService;


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
    public void addProduct(Product chosenProduct) {
        User user = userDetailsService.getUser();

        if (user.getShoppingCart() == null) {
            log.info("user doesnt have active cart, creating a new shopping cart");
            createShoppingCart(user);
        }
        ShoppingCart shoppingCart = getByUser(user);
        List<OrderedProduct> orderedProducts = shoppingCart.getOrderedProducts();

        boolean alreadyAdded = false;
        for (OrderedProduct orderedProduct : orderedProducts) {
            Product productInCart = orderedProduct.getProduct();
            if (chosenProduct.equals(productInCart)) {
                log.info("product id=" + chosenProduct.getId() + " is already ordered. Increasing quantity by 1");
                alreadyAdded = true;
                orderedProduct.setQuantity(orderedProduct.getQuantity() + 1);
                break;
            }
        }
        if (!alreadyAdded) {
            log.info("product id=" + chosenProduct.getId() + "have not been added to cart yet. Adding product");
            OrderedProduct newOrderedProduct = new OrderedProduct(shoppingCart, chosenProduct, 1);
            orderedProductService.save(newOrderedProduct);
            orderedProducts.add(newOrderedProduct);
        }
        updateTotalPriceAndDiscount(shoppingCart);

        shoppingCartRepository.save(shoppingCart);
        log.info("product: " + chosenProduct + " added to cart id = " + shoppingCart.getId());
    }

    private void createShoppingCart(User user) {
        log.info("start creating new cart for user login=" + user.getLogin());
        ShoppingCart shoppingCart = shoppingCartRepository.save(new ShoppingCart(user));
        userService.setShoppingCartForUser(shoppingCart, user);
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

        log.info("total price update finished");
    }

    @Transactional
    public void changeProductQuantity(OrderedProduct newQuantityObject) {
        User user = userDetailsService.getUser();

        ShoppingCart cart = shoppingCartRepository.findByUser(user);
        OrderedProduct orderedProduct = orderedProductService.getByCartAndId(cart, newQuantityObject.getId());

        orderedProduct.setQuantity(newQuantityObject.getQuantity());
        updateTotalPriceAndDiscount(cart);
    }

    @Transactional
    public void deleteOrderedProduct(String orderedProductId) {
        User user = userDetailsService.getUser();

        Long id = Long.parseLong(orderedProductId);
        List<OrderedProduct> orderedProducts = user.getShoppingCart().getOrderedProducts();
        orderedProducts.removeIf((OrderedProduct product) -> {
            if (product.getId() == id) {
                orderedProductService.delete(product);
                return true;
            } else {
                return false;
            }
        });
        updateTotalPriceAndDiscount(user.getShoppingCart());
    }

    @Transactional
    public void deleteCart() {
        User user = userDetailsService.getUser();

        List<OrderedProduct> orderedProducts = user.getShoppingCart().getOrderedProducts();
        orderedProducts.forEach(product -> product.setShoppingCart(null));
        shoppingCartRepository.delete(user.getShoppingCart());
        userService.deleteCartFromUser(user);
    }

    @LogResult
    public Integer calculateItems(AbstractUser abstractUser) {
        User user = userService.getUserByLogin(abstractUser.getLogin());

        if (user.getShoppingCart() == null) {
            return 0;
        }
        List<OrderedProduct> orderedProducts = user.getShoppingCart().getOrderedProducts();

        return orderedProducts.size();
    }

}
