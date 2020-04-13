package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vnevzorov.Shop.model.Order;

import java.time.LocalDateTime;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Iterable<Order> findAllByDateGreaterThanEqual(LocalDateTime date);
}
