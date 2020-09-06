package ru.vnevzorov.Shop.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.Admin;
import ru.vnevzorov.Shop.model.user.Role;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private AbstractUser abstractUser;

    public UserDetailsImpl(AbstractUser abstractUser) {
        this.abstractUser = abstractUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(abstractUser.getRole().name()));
    }

    @Override
    public String getPassword() {
        return abstractUser.getPassword();
    }

    @Override
    public String getUsername() {
        return abstractUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        if (abstractUser.isPasswordChanged() == false) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //Админы должны менять пароль каждые 90 дней
        if (abstractUser.getRole() == Role.ADMIN) {
            Admin admin = (Admin) abstractUser;
            if (LocalDate.now().minusDays(90).isAfter(admin.getLastPasswordUpdate())) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public boolean isEnabled() {
        return abstractUser.isEnabled();
    }

    public AbstractUser getAbstractUser() {
        return abstractUser;
    }
}
