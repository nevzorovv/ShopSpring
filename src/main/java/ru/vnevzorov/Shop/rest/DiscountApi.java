package ru.vnevzorov.Shop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.model.Discount;
import ru.vnevzorov.Shop.service.DiscountService;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("api/discounts")
public class DiscountApi {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public Iterable<Discount> getAll() {
        return discountService.getAll();
    }

    @GetMapping("/{id}")
    public Discount getOne(@PathVariable Integer id) {
        return discountService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Discount save(@RequestBody @Valid Discount discount) {
        discountService.saveDiscount(discount);

        return discount;
    }

    @PutMapping("/{id}")
    public Discount update(@PathVariable Integer id, @RequestBody Discount discount) {
        discountService.updateById(id, discount);
        return discount;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        discountService.deleteById(id);
    }

}
