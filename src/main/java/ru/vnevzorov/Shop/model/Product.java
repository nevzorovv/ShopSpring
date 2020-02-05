package ru.vnevzorov.Shop.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen_seq")
    @SequenceGenerator(name = "product_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "product_seq")
    private Long id;

    private String manufacturer;
    private String model;
    private Double price;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Discount discount;

    @ManyToMany
    @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "product_supplier", joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private List<Supplier> suppliers = new ArrayList<>();

    public Product() {
    }

    public Product(String manufacturer, String model, Double price, Category category, Discount discount) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.category = category;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", discount=" + discount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(manufacturer, product.manufacturer) &&
                Objects.equals(model, product.model) &&
                Objects.equals(price, product.price) &&
                Objects.equals(category, product.category) &&
                Objects.equals(discount, product.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, manufacturer, model, price, category, discount);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public Long getId() {
        return id;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
