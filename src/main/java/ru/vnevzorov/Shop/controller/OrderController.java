package ru.vnevzorov.Shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.User;
import ru.vnevzorov.Shop.service.OrderService;
import ru.vnevzorov.Shop.service.ShoppingCartService;
import ru.vnevzorov.Shop.service.UserService;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping("orderinformation")
    public ModelAndView showOrderForm(@RequestParam("id") String cartId) {
        String viewName = "jsp/orderinformation_form.jsp";
        ModelAndView modelAndView = new ModelAndView(viewName);

        Order order = orderService.prepareNewOrder(cartId);

        modelAndView.addObject("cartId", cartId);
        modelAndView.addObject("order", order);
        modelAndView.addObject("paymentTypes", Arrays.asList("cash", "online", "debit"));
        modelAndView.addObject("shipmentTypes", Arrays.asList("courier", "pickpoint", "pickup"));

        return modelAndView;
    }

    @PostMapping("saveNewOrder")
    public RedirectView saveNewOrder(@ModelAttribute Order order, @RequestParam("id") String cartId) {
        String url = "thankYouForOrder";
        User user = userService.getByShoppingCartId(cartId);
        order.setUser(user);

        orderService.saveOrder(order);

        return new RedirectView(url);
    }

    @GetMapping("thankYouForOrder")
    public ModelAndView getOrderService() {
        ModelAndView modelAndView = new ModelAndView("jsp/thankYouForOrder.jsp");

        return modelAndView;
    }
}
