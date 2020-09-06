package ru.vnevzorov.Shop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.service.OrderService;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/orders")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public Iterable<Order> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public Order getOne(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order save(@RequestBody @Valid Order order) {
        orderService.saveNewOrder(order);

        return order;
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody Order order) {
        throw new MethodNotAllowedException(HttpMethod.POST, Collections.singleton(HttpMethod.GET));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.deleteById(id);
    }

}
