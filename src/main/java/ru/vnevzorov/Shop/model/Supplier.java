package ru.vnevzorov.Shop.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_gen_seq")
    @SequenceGenerator(name = "supplier_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "supplier_seq")
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "product_supplier", joinColumns = @JoinColumn(name = "supplier_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    public Supplier() {
    }

    public Supplier(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(name, supplier.name) &&
                Objects.equals(id, supplier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
