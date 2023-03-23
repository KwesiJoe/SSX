package io.staxex.api.bankAccountManagement.controllers;

import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.bankAccountManagement.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bankaccounts")
public class BankAccountsController {
    final
    BankAccountRepository bankAccountRepository;

    public BankAccountsController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBankAccount(@RequestBody BankAccount bankAccountdetails){

        bankAccountRepository.save(bankAccountdetails);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/")
    public String getOrders(){
        return "all orders";
    }

}
