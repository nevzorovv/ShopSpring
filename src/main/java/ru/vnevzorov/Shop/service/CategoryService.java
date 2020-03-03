package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getByName(String name) {
        return categoryRepository.findByName(name);
    }
}
