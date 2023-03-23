package io.staxex.api.authentication.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class SecureTraderDetails implements UserDetails {

    private Trader trader;

    public SecureTraderDetails(Trader trader) {
        this.trader = trader;
    }
    public Long getId(){
        return trader.getId();
    }

    public String getFirstName(){
        return trader.getFirstName();
    }

    public String getLastName(){
        return trader.getLastName();
    }
    @Override
    public String getUsername() {
        return trader.getEmail();
    }

    @Override
    public String getPassword() {
        return trader.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return trader.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
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
