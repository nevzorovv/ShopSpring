package ru.vnevzorov.Shop.service.report;

import ru.vnevzorov.Shop.model.OrderedProduct;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDataModel {

    private Long id;
    private LocalDateTime date;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderedProduct> products;
    private Double tottalPrice;
    private String payment;
    private String shipment;
    private String status;

    public OrderDataModel() {
    }

    public OrderDataModel(Long id, LocalDateTime date, String login, String firstName, String lastName, String email, List<OrderedProduct> products, Double tottalPrice, String payment, String shipment, String status) {
        this.id = id;
        this.date = date;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.products = products;
        this.tottalPrice = tottalPrice;
        this.payment = payment;
        this.shipment = shipment;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedProduct> products) {
        this.products = products;
    }

    public Double getTotalPrice() {
        return tottalPrice;
    }

    public void setTottalPrice(Double tottalPrice) {
        this.tottalPrice = tottalPrice;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
