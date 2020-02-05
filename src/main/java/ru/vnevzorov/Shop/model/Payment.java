package ru.vnevzorov.Shop.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_gen_seq")
    @SequenceGenerator(name = "payment_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "payment_seq")
    Long id;

    @Column(unique = true)
    String type;

    @OneToMany
    List<Order> orders = new ArrayList<>();

    public Payment() {
    }

    public Payment(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "type='" + type + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(type, payment.type) &&
                Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id);
    }

    public List<Order> getOrders() {
        return orders;
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
}
