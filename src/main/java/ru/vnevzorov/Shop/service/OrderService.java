package ru.vnevzorov.Shop.service;

import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.*;
import ru.vnevzorov.Shop.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
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

    public Order prepareNewOrder(String shoppingCartId) {
        User user = userService.getUserByLogin("firstUser"); //FIXME

        ShoppingCart cart = shoppingCartService.getById(shoppingCartId);
        Order order = new Order();
        order.setUser(user);
        order.setOrderedProducts(cart.getOrderedProducts());
        order.setTotalPrice(cart.getTotalPrice());

        return order;
    }

    @Transactional
    public void saveOrder(Order order) {
        User user = order.getUser();
        order.setTotalPrice(user.getShoppingCart().getTotalPrice());
        order.setPayment(paymentService.getPayment(order.getPayment().getType()));
        order.setShipment(shipmentService.getShipment(order.getShipment().getType()));
        order.setDate(LocalDateTime.now());
        setOrderedProducts(order);

        orderRepository.save(order);

        //order.setOrderedProducts(user.getShoppingCart().getOrderedProducts());


        shoppingCartService.deleteCart();
    }

    public void setOrderedProducts(Order order) {
        User user = order.getUser();
        List<OrderedProduct> orderedProducts = user.getShoppingCart().getOrderedProducts();
        orderedProducts.forEach(product -> product.setOrder(order));

        order.setOrderedProducts(orderedProducts);
    }

}
