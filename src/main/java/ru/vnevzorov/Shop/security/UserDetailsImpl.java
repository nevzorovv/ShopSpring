package ru.vnevzorov.Shop.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vnevzorov.Shop.model.user.AbstractUser;

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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return abstractUser.isEnabled();
    }

    public AbstractUser getAbstractUser() {
        return abstractUser;
    }
}
