package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vnevzorov.Shop.model.Category;

public interface CategoryRepository extends CrudRepository<Category, String> {
}
