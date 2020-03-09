package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.model.Payment;
import ru.vnevzorov.Shop.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment getPayment(String type) {
        return paymentRepository.findByType(type);
    }
}
