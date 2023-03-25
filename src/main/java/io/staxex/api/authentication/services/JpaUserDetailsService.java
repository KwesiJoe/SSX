package io.staxex.api.authentication.services;

import io.staxex.api.authentication.models.SecureTraderDetails;
import io.staxex.api.authentication.repositories.TraderRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {


    private final TraderRepository traderRepository;

    public JpaUserDetailsService(TraderRepository traderRepository) {
        this.traderRepository = traderRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return traderRepository.findByEmail(email)
                .map(SecureTraderDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found;" + email));
    }
}
