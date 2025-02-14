package ru.xast.SkillSwap.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.xast.SkillSwap.models.Users;

import java.util.Collection;
import java.util.Collections;

public class UsersDetails implements UserDetails {

    private final Users users;

    public UsersDetails(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(users.getRole()));
    }

    @Override
    public String getPassword() {
        return this.users.getPassword();
    }

    @Override
    public String getUsername() {
        return this.users.getUsername();
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
        return true;
    }

    public Users getUsers() {
        return users;
    }
}
