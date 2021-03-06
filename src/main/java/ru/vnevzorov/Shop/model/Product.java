package ru.vnevzorov.Shop.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen_seq")
    @SequenceGenerator(name = "product_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "product_seq")
    private Long id;

    @NotBlank(message = "field must be not empty")
    private String manufacturer;

    @NotBlank(message = "field must be not empty")
    private String model;

    @NotNull
    @Digits(integer = 7, fraction = 2, message = "value out of <7 digits>.<2 digits>")
    private Double price;
    
    @ManyToOne
    private Category category;

    @ManyToOne
    private Discount discount;

    @ManyToMany
    @JoinTable(name = "product_supplier", joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private List<Supplier> suppliers = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderedProduct> orderedProducts = new ArrayList<>();

    /***************Spring Data JPA Auditing*******************/
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    /***************Spring Data JPA Auditing*******************/

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
                Objects.equals(discount, product.discount) &&
                Objects.equals(suppliers, product.suppliers) &&
                Objects.equals(orderedProducts, product.orderedProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, manufacturer, model, price, category, discount, suppliers, orderedProducts);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
