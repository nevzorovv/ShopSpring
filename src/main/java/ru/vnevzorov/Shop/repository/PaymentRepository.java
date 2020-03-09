package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.vnevzorov.Shop.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {

    Payment findByType(String type);

}
