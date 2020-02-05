package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vnevzorov.Shop.model.Discount;

public interface DiscountRepository extends CrudRepository<Discount, Integer> {
}
