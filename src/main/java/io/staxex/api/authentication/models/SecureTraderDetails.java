package io.staxex.api.authentication.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecureTraderDetails implements UserDetails {

    private final Trader trader;

    public SecureTraderDetails(Trader trader) {
        this.trader = trader;
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
        return null;
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
