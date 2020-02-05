package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vnevzorov.Shop.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, String> {
}
