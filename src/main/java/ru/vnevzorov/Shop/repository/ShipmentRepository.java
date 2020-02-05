package ru.vnevzorov.Shop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vnevzorov.Shop.model.Shipment;

public interface ShipmentRepository extends CrudRepository<Shipment, String> {
}
