package ru.vnevzorov.Shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_gen_seq")
    @SequenceGenerator(name = "discount_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "discount_seq")
    private Integer id;

    private Double value;
    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "discount")
    private List<Product> products = new ArrayList<>();

    public Discount() {
    }

    public Discount(Double value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", value=" + value +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(value, discount.value) &&
                Objects.equals(id, discount.id) &&
                Objects.equals(type, discount.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, id, type);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Integer getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
