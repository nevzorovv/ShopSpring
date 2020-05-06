package ru.vnevzorov.Shop.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.vnevzorov.Shop.model.OrderedProduct;
import ru.vnevzorov.Shop.model.Product;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.user.Role;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.security.UserDetailsServiceImpl;
import ru.vnevzorov.Shop.service.CategoryService;
import ru.vnevzorov.Shop.service.OrderedProductService;
import ru.vnevzorov.Shop.service.ProductService;
import ru.vnevzorov.Shop.service.ShoppingCartService;
import ru.vnevzorov.Shop.service.user.UserService;

@Controller
public class ShoppingCartController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderedProductService orderedProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("addToCart")
    public RedirectView addToShoppingCart(@ModelAttribute Product chosenProductId) {
        log.info("POST: /addToCart id=" + chosenProductId.getId());

        Product chosenProduct = productService.getProductById(chosenProductId.getId());
        String url = "products" + "?category=" + chosenProduct.getCategory().getName();

        if (userDetailsService.getAbstractUser().getRole() != Role.USER) {
            log.error("The user has role=" + userDetailsService.getAbstractUser().getRole() + ". No permissions to create a shopping cart");
            return new RedirectView(url + "&allow=false");
        }

        shoppingCartService.addProduct(chosenProduct);

        return new RedirectView(url);
    }

    @GetMapping("shoppingcart")
    //@PreAuthorize("hasAnyAuthority('ADMIN')" /*and @accessService.test(#user)*/)  // для методов сервисов не заработало
    public ModelAndView showShoppingCart() {
        log.info("GET: /shoppingcart");

        String viewName = "jsp/shoppingcart.jsp";
        ModelAndView modelAndView = new ModelAndView(viewName);

        User user = userDetailsService.getUser();
        if (user.getShoppingCart() != null) {
            ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
            modelAndView.addObject("totalPrice", shoppingCart.getTotalPrice());
            modelAndView.addObject("totalDiscount", shoppingCart.getTotalDiscount());
            modelAndView.addObject("orderedProducts", shoppingCart.getOrderedProducts());
            modelAndView.addObject("shoppingCartId", shoppingCart.getId());
            modelAndView.addObject("newQuantityObject", new OrderedProduct());
        }

        log.info("returned: " + modelAndView.getViewName() + ", model: " + modelAndView.getModel());

        return modelAndView;
    }

    @PostMapping("changeQuantity")
    public RedirectView changeQuantity(@ModelAttribute OrderedProduct newQuantityObject) {
        String url = "shoppingcart";

        Integer newQuantity;
        try {
            newQuantity = newQuantityObject.getQuantity();
            if (newQuantity < 0) {
                throw  new IllegalArgumentException();
            }
        } catch (Exception e) {
            log.error("Exception: wrong format newQuantity = " + newQuantityObject.getQuantity());
            return new RedirectView("shoppingcart"); //TODO показать сообщение, что введены неверные данные
        }
        shoppingCartService.changeProductQuantity(newQuantityObject);

        return new RedirectView(url);
    }

    @GetMapping("deleteOrderedProduct")
    public RedirectView deleteOrderedProduct(@RequestParam("id") String orderedProductId) {
        String url = "shoppingcart";

        shoppingCartService.deleteOrderedProduct(orderedProductId);

        return new RedirectView(url);
    }



}
