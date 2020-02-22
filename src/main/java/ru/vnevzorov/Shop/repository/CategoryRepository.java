package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.model.Product;


public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Category findByName(String name);
}
