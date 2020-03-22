package ru.vnevzorov.Shop.model.user;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Admin extends AbstractUser {
    //TODO добавить уникальные поля для админа
    private String testField1;
    private String testField2;

    public Admin() {
    }

    public Admin(String login, String password, String firstName, String lastName, String email, String testField1, String testField2) {
        super(login, password, firstName, lastName, email);
        this.testField1 = testField1;
        this.testField2 = testField2;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "testField1='" + testField1 + '\'' +
                ", testField2='" + testField2 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(testField1, admin.testField1) &&
                Objects.equals(testField2, admin.testField2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), testField1, testField2);
    }

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
}
