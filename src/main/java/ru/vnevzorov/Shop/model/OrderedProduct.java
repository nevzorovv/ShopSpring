package ru.vnevzorov.Shop.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderedproduct_gen_seq")
    @SequenceGenerator(name = "orderedproduct_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "orderedproduct_seq")
    private Long id;

    @ManyToOne()
    private ShoppingCart shoppingCart;

    @ManyToOne
    private Order order;

    @ManyToOne()
    private Product product;

    private int quantity;

    /***************Spring Data JPA Auditing*******************/
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    /***************Spring Data JPA Auditing*******************/

    public OrderedProduct() {
    }

    public OrderedProduct(ShoppingCart cart, Product product, int quantity) {
        this.shoppingCart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "id=" + id +
                ", shoppingCart=" + shoppingCart +
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedProduct that = (OrderedProduct) o;
        return quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(shoppingCart, that.shoppingCart) &&
                Objects.equals(order, that.order) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shoppingCart, order, product, quantity);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
