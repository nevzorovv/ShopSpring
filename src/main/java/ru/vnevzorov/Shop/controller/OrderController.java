package ru.vnevzorov.Shop.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.vnevzorov.Shop.enumeration.Status;
import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.service.OrderService;
import ru.vnevzorov.Shop.service.ShoppingCartService;
import ru.vnevzorov.Shop.service.email.EmailService;
import ru.vnevzorov.Shop.service.report.ReportService;
import ru.vnevzorov.Shop.service.user.UserService;

import java.util.Arrays;

@Controller
public class OrderController {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    EmailService emailService;

    @Autowired
    ReportService reportService;

    @GetMapping("orderinformation")
    public ModelAndView showOrderForm(@RequestParam("id") String cartId) {
        String viewName = "jsp/orderinformation_form.jsp";
        ModelAndView modelAndView = new ModelAndView(viewName);

        //Order order = orderService.prepareNewOrder(cartId);

        modelAndView.addObject("cartId", cartId);
        modelAndView.addObject("order", new Order());
        modelAndView.addObject("paymentTypes", Arrays.asList("cash", "online", "debit"));
        modelAndView.addObject("shipmentTypes", Arrays.asList("courier", "pickpoint", "pickup"));

        return modelAndView;
    }

    @PostMapping("order_confirmed")
    @PreAuthorize("hasAnyAuthority('USER')")
    public RedirectView saveNewOrder(@ModelAttribute Order order) {
        log.info("GET: /saveNewOrder");
        String url = "thankYouForOrder";

        orderService.saveNewOrder(order);

        return new RedirectView(url);
    }

    @GetMapping("thankYouForOrder")
    public ModelAndView getOrderService() {
        ModelAndView modelAndView = new ModelAndView("jsp/thankYouForOrder.jsp");

        return modelAndView;
    }

    @GetMapping("order_management")
    public ModelAndView showOrderManagementPage() {
        String viewName = "jsp/order_management.jsp";
        ModelAndView modelAndView = new ModelAndView(viewName);

        modelAndView.addObject("orders", orderService.getAll());
        modelAndView.addObject("newStatus", new Order());
        modelAndView.addObject("statusCREATED", Status.statusMap.get(Status.CREATED));
        modelAndView.addObject("statusCONFIRMED", Status.statusMap.get(Status.CONFIRMED));
        modelAndView.addObject("statusINPROGRESS", Status.statusMap.get(Status.IN_PROGRESS));

        return modelAndView;
    }

    @PostMapping("status_change")
    public RedirectView changeOrderStatus(@ModelAttribute Order newStatus) {
        String url = "jsp/order_management.jsp";
        log.info("GET: /status_change");

        orderService.changeOrderStatus(newStatus);

        return new RedirectView("order_management");
    }
}
