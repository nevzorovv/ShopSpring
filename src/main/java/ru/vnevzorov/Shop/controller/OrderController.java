package ru.vnevzorov.Shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    @GetMapping("orderinformation")
    public ModelAndView showOrderForm(@RequestParam String cartId) {
        ModelAndView modelAndView = new ModelAndView("jsp/order_form.jsp");

        modelAndView.addObject("cartId", cartId);

        return modelAndView;
    }
}
