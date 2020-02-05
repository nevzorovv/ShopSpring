package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vnevzorov.Shop.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
