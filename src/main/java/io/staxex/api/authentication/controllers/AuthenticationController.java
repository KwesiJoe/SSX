package io.staxex.api.authentication.controllers;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.payload.request.LoginRequest;
import io.staxex.api.authentication.payload.request.SignupRequest;
import io.staxex.api.authentication.payload.response.JwtResponse;
import io.staxex.api.authentication.services.AuthenticationService;
import io.staxex.api.exceptions.TraderNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Trader> createTrader(@Valid @RequestBody SignupRequest trader) throws TraderNotFoundException {
        Trader newTrader = new Trader(trader.getFirstName(), trader.getLastName(), trader.getEmail(), trader.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.createTrader(newTrader));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody LoginRequest credentials){
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.authenticateUser(credentials));
    }



}
