package io.staxex.api.authentication.services;

import io.staxex.api.authentication.enums.ERole;
import io.staxex.api.authentication.models.Role;
import io.staxex.api.authentication.models.SecureTraderDetails;
import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.payload.request.LoginRequest;
import io.staxex.api.authentication.payload.response.JwtResponse;
import io.staxex.api.authentication.repositories.RoleRepository;
import io.staxex.api.authentication.repositories.TraderRepository;
import io.staxex.api.exceptions.TraderNotFoundException;
import io.staxex.api.wallets.services.WalletService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final TraderRepository traderRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    private final WalletService walletService;

    public AuthenticationService(TraderRepository traderRepository, PasswordEncoder passwordEncoder, /*, AuthenticationManager authenticationManager*/RoleRepository roleRepository, TokenService tokenService, AuthenticationManager authenticationManager, WalletService walletService) {
        this.traderRepository = traderRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.walletService = walletService;
    }

    public Trader createTrader(Trader trader) throws TraderNotFoundException {
        if (traderRepository.existsByEmail(trader.getEmail().toLowerCase())) {
            throw new TraderNotFoundException("Trader not found");
        }

        Trader newTrader = new Trader(
                trader.getFirstName(),
                trader.getLastName(),
                trader.getEmail().toLowerCase(),
                passwordEncoder.encode(trader.getPassword())
        );

        Set<Role> role = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_TRADER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        role.add(userRole);

        newTrader.setRoles(role);

        newTrader = traderRepository.save(newTrader);

        return newTrader;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail().toLowerCase(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = tokenService.generateToken(authentication);
        SecureTraderDetails traderDetails = (SecureTraderDetails) authentication.getPrincipal();

        List<String> roles = traderDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(
                jwtToken,
                traderDetails.getId(),
                traderDetails.getFirstName(),
                traderDetails.getLastName(),
                traderDetails.getUsername(),
                roles
        );
    }

}
