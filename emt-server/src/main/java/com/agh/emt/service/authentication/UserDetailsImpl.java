package com.agh.emt.service.authentication;

import com.agh.emt.model.authentication.UserCredentials;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    @Id
    private String id;
    private String username;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public String getId() {
        return id;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public static UserDetailsImpl build(UserCredentials userCredentials) {
        return new UserDetailsImpl(
                userCredentials.getId(),
                userCredentials.getEmail(),
                userCredentials.getPassword(),
                List.of(new SimpleGrantedAuthority( userCredentials.getRole().name()))
        );
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
}
