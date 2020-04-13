package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.Shipment;
import ru.vnevzorov.Shop.repository.ShipmentRepository;

@Service
public class ShipmentService {

    @Autowired
    ShipmentRepository shipmentRepository;

    @Cacheable("shipment")
    public Shipment getShipment(String type) {
        return shipmentRepository.findByType(type);
    }
}
