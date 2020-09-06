package ru.vnevzorov.Shop.rest.dto.user;

import ru.vnevzorov.Shop.model.user.Role;

import java.time.LocalDate;
import java.util.Objects;

public class AbstractUserRestDTO {
    private Long id;

    private Role role = Role.USER;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private String email;

    private boolean enabled;

    private boolean passwordChanged;

    public AbstractUserRestDTO() {
    }

    public AbstractUserRestDTO(Long id, Role role, String login, String password, String firstName, String lastName, LocalDate birthday, String email, boolean enabled, boolean passwordChanged) {
        this.id = id;
        this.role = role;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.enabled = enabled;
        this.passwordChanged = passwordChanged;
    }

    @Override
    public String toString() {
        return "AbstractUserRestDTO{" +
                "id=" + id +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", passwordChanged=" + passwordChanged +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractUserRestDTO that = (AbstractUserRestDTO) o;
        return enabled == that.enabled &&
                passwordChanged == that.passwordChanged &&
                Objects.equals(id, that.id) &&
                role == that.role &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, login, password, firstName, lastName, birthday, email, enabled, passwordChanged);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }
}
