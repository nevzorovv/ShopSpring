/*
package ru.vnevzorov.Shop.model;


*/
/*import ru.vnevzorov.Shop.model.user.AbstractUser;*//*


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User */
/*extends AbstractUser*//*
 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen_seq")
    @SequenceGenerator(name = "user_gen_seq", initialValue = 1, allocationSize = 1, sequenceName = "user_seq")
    private Long id;
    private String login;
    private String password;
    private String firstName;

    @Column(unique = true, nullable = false)
    private String email;

    //Тут колонка не создается. Сюда подтянутся все заказы юзера
    @OneToMany(mappedBy = "user") // "user" - это поле в Order
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private ShoppingCart shoppingCart;

    public User() {}

    public User(String login, String password, String firstName, String email) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email */
/*+ '\'' +
                ", shoppingCart=" + shoppingCart*//*
 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(orders, user.orders) &&
                Objects.equals(shoppingCart, user.shoppingCart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, firstName, email, orders, shoppingCart);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
*/
