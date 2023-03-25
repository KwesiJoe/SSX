package io.staxex.api.wallets.controllers;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.repositories.TraderRepository;
import io.staxex.api.authentication.services.TokenService;
import io.staxex.api.wallets.models.Wallet;
import io.staxex.api.wallets.services.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/wallet")
public class WalletController {


    private WalletService walletService;

    @Autowired
    TokenService tokenService;
    @Autowired
    TraderRepository traderRepository;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @PutMapping("/load")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_TRADER','SCOPE_ROLE_ADMIN')")
    public ResponseEntity<Wallet> loadWallet(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody BigDecimal amount){
        String token = Objects.requireNonNull(authorization.substring(7));
        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        return ResponseEntity.status(HttpStatus.OK)
                .body(walletService.loadWallet(trader, amount));
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_TRADER','SCOPE_ROLE_ADMIN')")
    public ResponseEntity<Wallet> getWallet(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String token = Objects.requireNonNull(authorization.substring(7));
        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        return ResponseEntity.status(HttpStatus.OK)
                .body(walletService.getWallet(trader));
    }


}
