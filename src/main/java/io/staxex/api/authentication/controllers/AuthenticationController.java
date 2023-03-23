package io.staxex.api.authentication.controllers;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.repositories.TraderRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final TraderRepository traderRepository;
    private final PasswordEncoder passwordEncoder;

//    private final AuthenticationManager authenticationManager;

    public AuthenticationController(TraderRepository traderRepository, PasswordEncoder passwordEncoder /*, AuthenticationManager authenticationManager*/) {
        this.traderRepository = traderRepository;
        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createTrader(@Valid @RequestBody Trader trader){
        if (traderRepository.existsByEmail(trader.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email already in use");
        }

        Trader newTrader = new Trader(
                trader.getFirstName(),
                trader.getLastName(),
                trader.getEmail(),
                passwordEncoder.encode(trader.getPassword())
        );
        traderRepository.save(newTrader);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(){
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
