package io.staxex.api.bankAccountManagement.controllers;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.bankAccountManagement.repositories.BankAccountRepository;
import io.staxex.api.bankAccountManagement.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/bankaccounts")
@PreAuthorize("isAuthenticated()")
public class BankAccountsController {
    private BankAccountService bankAccountService;

    public BankAccountsController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/add")
    public ResponseEntity<BankAccount> addBankAccount(@RequestBody BankAccount bankAccountdetails){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankAccountService.addBankAccount(bankAccountdetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<BankAccount>> getBankAccount(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(bankAccountService.getBankAccount(id));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<BankAccount> updateAccountDetails(@PathVariable Long id, @RequestBody BankAccount bankAccount){
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.updateBankAccountDetails(bankAccount,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id){
        bankAccountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
