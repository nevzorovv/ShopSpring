package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.exception.NotFoundException;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Cacheable("all_categories")
    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Cacheable("category_by_name")
    public Category getByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category was not found"));
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
