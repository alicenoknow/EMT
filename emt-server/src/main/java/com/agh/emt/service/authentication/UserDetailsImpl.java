package com.agh.emt.service.authentication;

import com.agh.emt.model.admin.Admin;
import com.agh.emt.model.student.Student;
import com.agh.emt.utils.authentication.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Value
public class UserDetailsImpl implements UserDetails {
    BigInteger _id;
    String username;

    @JsonIgnore
    String password;
    Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Student student) {
        return new UserDetailsImpl(
                student.get_id(),
                student.getEmail(),
                student.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.STUDENT.name()))
        );
    }

    public static UserDetailsImpl build(Admin admin) {
        return new UserDetailsImpl(
                admin.get_id(),
                admin.getEmail(),
                admin.getPassword(),
                List.of(new SimpleGrantedAuthority(admin.getRole().name()))
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
