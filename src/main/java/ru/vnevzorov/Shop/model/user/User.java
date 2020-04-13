package ru.vnevzorov.Shop.model.user;

import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.model.ShoppingCart;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User extends AbstractUser {

    //TODO добавить уникальные поля для простого юзера

    private String testField1;
    private String testField2;

    //Тут колонка не создается. Сюда подтянутся все заказы юзера
    @OneToMany(mappedBy = "user") // "user" - это поле в Order
    private List<Order> orders = new ArrayList<>();

    @OneToOne(/*mappedBy = "user"*/)
    private ShoppingCart shoppingCart;

    public User() {
    }

    public User(String login, String password, String firstName, String lastName, String email, String testField1, String testField2) {
        super(login, password, firstName, lastName, email);
        this.testField1 = testField1;
        this.testField2 = testField2;
    }

    @Override
    public String toString() {
        return "User{" +
                "testField1='" + testField1 + '\'' +
                ", testField2='" + testField2 + '\'' +
                ", shoppingCart=" + shoppingCart +
                '}';
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(testField1, user.testField1) &&
                Objects.equals(testField2, user.testField2) &&
                Objects.equals(orders, user.orders) &&
                Objects.equals(shoppingCart, user.shoppingCart);
    }*/

    /*@Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), testField1, testField2, orders, shoppingCart);
    }*/

    public String getTestField1() {
        return testField1;
    }

    public void setTestField1(String testField1) {
        this.testField1 = testField1;
    }

    public String getTestField2() {
        return testField2;
    }

    public void setTestField2(String testField2) {
        this.testField2 = testField2;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
