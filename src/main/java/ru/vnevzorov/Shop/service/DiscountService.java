package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.Discount;
import ru.vnevzorov.Shop.repository.DiscountRepository;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Transactional
    public void saveDiscount(String discountValue_str, String discountType) {
        Double discountValue = Double.parseDouble(discountValue_str);

        discountRepository.save(new Discount(discountValue, discountType));
    }

}
