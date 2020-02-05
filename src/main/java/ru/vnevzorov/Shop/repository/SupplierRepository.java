package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vnevzorov.Shop.model.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, String> {
}
