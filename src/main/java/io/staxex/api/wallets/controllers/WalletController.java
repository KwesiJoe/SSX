package io.staxex.api.wallets.controllers;

import io.staxex.api.DTO.BankAccountDTO;
import io.staxex.api.DTO.CreateWalletDTO;
import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.repositories.TraderRepository;
import io.staxex.api.authentication.services.TokenService;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.exceptions.WalletNotFoundException;
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
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
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

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_TRADER','SCOPE_ROLE_ADMIN')")
    public ResponseEntity<Wallet> addWallet(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody CreateWalletDTO createWalletPayload){
        String token = Objects.requireNonNull(authorization.substring(7));
        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        Wallet wallet = new Wallet(trader, createWalletPayload.getCurrency(), createWalletPayload.getAmount());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(walletService.addWallet(wallet));
    }

    @PutMapping("/load")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_TRADER','SCOPE_ROLE_ADMIN')")
    public ResponseEntity<Wallet> loadWallet(/*@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,*/ @RequestBody Wallet wallet, @RequestBody BigDecimal amount) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(walletService.loadWallet(wallet, amount));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_TRADER','SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<Wallet>> getWallets(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String token = Objects.requireNonNull(authorization.substring(7));
        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        return ResponseEntity.status(HttpStatus.OK)
                .body(walletService.getWallets(trader));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_TRADER','SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> deleteWallet(@PathVariable Long id){
        walletService.deleteWallet(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
