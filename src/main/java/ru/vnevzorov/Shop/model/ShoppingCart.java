package ru.vnevzorov.Shop.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_gen_seq")
    @SequenceGenerator(name = "cart_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "cart_seq")
    private Long id;

    private Double totalPrice;

    private Double totalDiscount;

    @OneToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.PERSIST) //orphanRemoval = true указывает, что все объекты orderedProduct, не имеющие ссылку на корзину, будут удалены
    private List<OrderedProduct> orderedProducts = new ArrayList<>();

    /*@ManyToMany
    @JoinTable(name = "shoppingcart_product", joinColumns = @JoinColumn(name = "shoppingcart_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();*/

    public ShoppingCart() {
    }

    public ShoppingCart(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", totalDiscount=" + totalDiscount +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(totalPrice, that.totalPrice) &&
                Objects.equals(totalDiscount, that.totalDiscount) &&
                Objects.equals(user, that.user) &&
                Objects.equals(orderedProducts, that.orderedProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, totalDiscount, user, orderedProducts);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
