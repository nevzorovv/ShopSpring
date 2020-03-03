package ru.vnevzorov.Shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.vnevzorov.Shop.model.Product;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.User;
import ru.vnevzorov.Shop.service.ProductService;
import ru.vnevzorov.Shop.service.ShoppingCartService;
import ru.vnevzorov.Shop.service.UserService;

@Controller
public class ShoppingCartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @GetMapping("addToCart")
    public RedirectView addToShoppingCart(@RequestParam("id") String productId, @RequestParam("category") String categoryName) {
        RedirectView redirectView = new RedirectView("products" + "?category=" + categoryName);

        Product product = productService.getProductById(productId);
        User user = userService.getUserByLogin("firstUser");
        if (user.getShoppingCart() == null) {
            shoppingCartService.createShoppingCart(user);
        }
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        shoppingCartService.addProduct(shoppingCart, product);

        return redirectView;
    }

    @GetMapping("shoppingcart")
    public ModelAndView showShoppingCart() {
        ModelAndView modelAndView = new ModelAndView("jsp/shoppingcart.jsp");

        User user = userService.getUserByLogin("firstUser");
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        modelAndView.addObject("totalPrice", shoppingCart.getTotalPrice());
        modelAndView.addObject("totalDiscount", shoppingCart.getTotalDiscount());
        modelAndView.addObject("buyingProducts", shoppingCart.getProducts());
        modelAndView.addObject("shoppingCartId", shoppingCart.getId());

        return modelAndView;
    }

}
