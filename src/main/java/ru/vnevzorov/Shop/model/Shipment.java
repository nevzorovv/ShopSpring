package ru.vnevzorov.Shop.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_gen_seq")
    @SequenceGenerator(name = "shipment_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "shipment_seq")
    Long id;

    @Column(unique = true)
    String type;

    @OneToMany
    List<Order> orders = new ArrayList<>();

    public Shipment() {
    }

    public Shipment(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return Objects.equals(id, shipment.id) &&
                Objects.equals(type, shipment.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
