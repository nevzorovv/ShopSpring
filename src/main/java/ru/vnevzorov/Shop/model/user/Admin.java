package ru.vnevzorov.Shop.model.user;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
public class Admin extends AbstractUser {
    //TODO добавить уникальные поля для админа
    private String testField1;
    private String testField2;

    private boolean sendReports;

    public Admin() {
        super();
    }

    public Admin(String login, String password, String firstName, String lastName, LocalDate birthday, String email, String testField1, String testField2, boolean sendReports) {
        super(login, password, firstName, lastName, birthday, email);
        this.testField1 = testField1;
        this.testField2 = testField2;
        this.sendReports = sendReports;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "testField1='" + testField1 + '\'' +
                ", testField2='" + testField2 + '\'' +
                ", sendReports=" + sendReports +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return sendReports == admin.sendReports &&
                Objects.equals(testField1, admin.testField1) &&
                Objects.equals(testField2, admin.testField2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), testField1, testField2, sendReports);
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

    public boolean isSendReports() {
        return sendReports;
    }

    public void setSendReports(boolean sendReports) {
        this.sendReports = sendReports;
    }
}
