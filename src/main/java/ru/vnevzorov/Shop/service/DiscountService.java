package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.exception.NotFoundException;
import ru.vnevzorov.Shop.model.Discount;
import ru.vnevzorov.Shop.repository.DiscountRepository;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    public void saveDiscount(String discountValue_str, String discountType) {
        Double discountValue = Double.parseDouble(discountValue_str);

        discountRepository.save(new Discount(discountValue, discountType));
    }

    public void saveDiscount(Discount discount) {
        discountRepository.save(discount);
    }

    @Transactional
    public void updateById(Integer id, Discount discount) {
        Discount discountFromDB = getById(id);
        saveDiscount(discount);
    }

    public Iterable<Discount> getAll() {
        return discountRepository.findAll();
    }

    public Discount getById(Integer id) {
        return discountRepository.findById(id).orElseThrow(() -> new NotFoundException("Discount was not found"));
    }

    public void deleteById(Integer id) {
        discountRepository.deleteById(id);
    }

}
