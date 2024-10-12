package br.com.riume.backendeureka.security;

import br.com.riume.backendeureka.model.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Getter
    private UUID id;
    private String email;
    private String password;
    private boolean active;
    private boolean deleted;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS(UUID id, String email, String password, boolean active, boolean deleted, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.active = active;
        this.deleted = deleted;
        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active && !deleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active && !deleted;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active && !deleted;
    }
}
