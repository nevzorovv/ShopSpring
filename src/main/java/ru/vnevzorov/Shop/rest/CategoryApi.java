package ru.vnevzorov.Shop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.service.CategoryService;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("api/categories")
public class CategoryApi {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Iterable<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category getOne(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category save(@RequestBody @Valid Category category) {
        throw new MethodNotAllowedException(HttpMethod.POST, Collections.singleton(HttpMethod.GET));
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Integer id, @RequestBody Category category) {
        throw new MethodNotAllowedException(HttpMethod.POST, Collections.singleton(HttpMethod.GET));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        categoryService.deleteById(id);
    }
}
