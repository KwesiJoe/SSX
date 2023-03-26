package io.staxex.api.bankAccountManagement.controllers;

import io.staxex.api.DTO.BankAccountDTO;
import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.repositories.TraderRepository;
import io.staxex.api.authentication.services.TokenService;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.bankAccountManagement.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/bankaccounts")
public class BankAccountsController {
    private BankAccountService bankAccountService;

    @Autowired
    TokenService tokenService;
    @Autowired
    TraderRepository traderRepository;

    public BankAccountsController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_TRADER','SCOPE_ROLE_ADMIN')")
    public ResponseEntity<BankAccount> addBankAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody BankAccountDTO bankAccountdetails){
        String token = Objects.requireNonNull(authorization.substring(7));
        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        BankAccount bankAccount = new BankAccount(
                bankAccountdetails.getIdentifier(),
                trader,
                bankAccountdetails.getBankName(),
                bankAccountdetails.getAccountName(),
                bankAccountdetails.getAccountNumber()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankAccountService.addBankAccount(bankAccount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<BankAccount>> getBankAccount(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(bankAccountService.getBankAccount(id));
    }

    @GetMapping()
    public ResponseEntity<List<BankAccount>> getBankAccounts(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String token = Objects.requireNonNull(authorization.substring(7));
        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        return ResponseEntity.status(HttpStatus.OK)
                .body(bankAccountService.getAllBankAccounts(trader));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<BankAccount> updateAccountDetails(@PathVariable Long id, @RequestBody BankAccountDTO bankAccountdetails){
        BankAccount bankAccount = new BankAccount(
                bankAccountdetails.getBankName(),
                bankAccountdetails.getAccountName(),
                bankAccountdetails.getAccountNumber()
        );
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.updateBankAccountDetails(bankAccount,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id){
        bankAccountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
