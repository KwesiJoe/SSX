package io.staxex.api.wallets.controllers;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.wallets.models.Wallet;
import io.staxex.api.wallets.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PutMapping("/load")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Wallet> loadWallet(@RequestBody Trader trader, @RequestBody BigDecimal amount){
        return ResponseEntity.status(HttpStatus.OK)
                .body(walletService.loadWallet(trader, amount));
    }

    @GetMapping("/")
    public ResponseEntity<Wallet> getWallet(@RequestBody Trader trader){
        return ResponseEntity.status(HttpStatus.OK)
                .body(walletService.getWallet(trader));
    }
}
