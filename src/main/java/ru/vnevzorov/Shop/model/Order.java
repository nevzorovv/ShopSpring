package ru.vnevzorov.Shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.vnevzorov.Shop.enumeration.Status;
import ru.vnevzorov.Shop.model.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_gen_seq")
    @SequenceGenerator(name = "order_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "order_seq")
    private Long id;
    private String number;

    @Column()
    private LocalDateTime date;
    private Double totalPrice;

    @ManyToOne()
    private User user;

    @ManyToOne
    private Payment payment;

    @ManyToOne
    private Shipment shipment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "order")
    private List<OrderedProduct> orderedProducts = new ArrayList<>();

    /***************Spring Data JPA Auditing*******************/
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Timestamp createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    /***************Spring Data JPA Auditing*******************/

    public Order() {
    }

    public Order(String number, LocalDateTime date, User user, Payment payment, Double totalPrice, Shipment shipment, Status status) {
        this.number = number;
        this.date = date;
        this.user = user;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.shipment = shipment;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                ", user=" + user +
                ", payment=" + payment +
                ", shipment=" + shipment +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(number, order.number) &&
                Objects.equals(date, order.date) &&
                Objects.equals(totalPrice, order.totalPrice) &&
                Objects.equals(user, order.user) &&
                Objects.equals(payment, order.payment) &&
                Objects.equals(shipment, order.shipment) &&
                status == order.status &&
                Objects.equals(orderedProducts, order.orderedProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, date, totalPrice, user, payment, shipment, status, orderedProducts);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
