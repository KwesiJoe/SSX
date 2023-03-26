package io.staxex.api.bankAccountManagement.models;

import io.staxex.api.authentication.models.Trader;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccount {
    @Id
    @SequenceGenerator(
            name = "bankAccountSequence",
            sequenceName = "bankAccountSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bankAccountSequence"
    )
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Trader trader;
    private String identifier;
    private String bankName;
    private String accountName;
    private String accountNumber;

    public BankAccount(String identifier, Trader trader, String bankName, String accountName, String accountNumber) {
        this.identifier= identifier;
        this.trader = trader;
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }
    public BankAccount(Trader trader, String bankName, String accountName, String accountNumber) {
        this.trader = trader;
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }

    public BankAccount(String bankName, String accountName, String accountNumber) {
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }

}
