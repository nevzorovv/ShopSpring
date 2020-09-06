package ru.vnevzorov.Shop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.enumeration.Status;
import ru.vnevzorov.Shop.exception.NotFoundException;
import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.model.OrderedProduct;
import ru.vnevzorov.Shop.model.ShoppingCart;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.repository.OrderRepository;
import ru.vnevzorov.Shop.security.UserDetailsServiceImpl;
import ru.vnevzorov.Shop.service.email.EmailService;
import ru.vnevzorov.Shop.service.email.Message;
import ru.vnevzorov.Shop.service.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    OrderedProductService orderedProductService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order was not found"));
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Deprecated
    public Order prepareNewOrder(String shoppingCartId) {
        User user = userDetailsService.getUser();

        ShoppingCart cart = shoppingCartService.getById(shoppingCartId);
        Order order = new Order();
        order.setUser(user);
        order.setOrderedProducts(cart.getOrderedProducts());
        order.setTotalPrice(cart.getTotalPrice());

        return order;
    }

    @Transactional
    public void saveNewOrder(Order order) {
        User user = userDetailsService.getUser();
        order.setUser(user);
        order.setTotalPrice(user.getShoppingCart().getTotalPrice());
        order.setPayment(paymentService.getPayment(order.getPayment().getType()));
        order.setShipment(shipmentService.getShipment(order.getShipment().getType()));
        order.setDate(LocalDateTime.now());
        order.setStatus(Status.CREATED);

        //Удаление корзины, передача списка заказанных продуктов из корзины в заказ
        List<OrderedProduct> orderedProducts = user.getShoppingCart().getOrderedProducts();
        shoppingCartService.deleteCart();

        orderRepository.save(order);
        orderedProducts.forEach(orderedProduct -> orderedProduct.setOrder(order));
        order.setOrderedProducts(orderedProducts);
    }

    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public void changeOrderStatus(Order orderStatus) {
        if (orderStatus == null || orderStatus.getStatus() == null || orderStatus.getStatus().equals("")) {
            throw new RuntimeException("неверные данные");
        }
        Order order = orderRepository.findById(orderStatus.getId()).get();
        Status newStatus = orderStatus.getStatus();
        order.setStatus(newStatus);
        log.info("order status changed: orderId=" + order.getId() + " newStatus=" + newStatus);

        String to = order.getUser().getEmail();
        String subject = "Order " + order.getId();
        String text = "Status of your order " + order.getId() + " was changed to "
                + order.getStatus() + "\r\nThank you for choosing our service";
        Message message = new Message(to, subject, text);
        emailService.sendMessage(message);
    }

    /*
    * Метод возвращает список всех заказов, начиная с определенной даты. Используется для отчетности
    */
    public Iterable<Order> getAllByDateGreaterThanEqual(LocalDate date) {
        LocalDateTime dateTime = date.atStartOfDay();
        return orderRepository.findAllByDateGreaterThanEqual(dateTime);
    }

}
