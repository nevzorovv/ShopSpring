package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vnevzorov.Shop.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
