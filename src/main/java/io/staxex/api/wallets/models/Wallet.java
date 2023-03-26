package io.staxex.api.wallets.models;

import io.staxex.api.authentication.models.Trader;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Wallet {
    @SequenceGenerator(
            name = "walletSequence",
            sequenceName = "walletSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "walletSequence"
    )
    @Id
    private Long id;
    @OneToOne
    private Trader trader;
    @Column(scale = 2, precision = 10)
    private BigDecimal balance;

    private String currency;

    public Wallet(Trader trader, String currency) {
        this.trader = trader;
        this.currency = currency;
        this.balance = BigDecimal.valueOf(0);
    }

    public Wallet(Trader trader, String currency, BigDecimal balance) {
        this.trader = trader;
        this.currency = currency;
        this.balance = balance;
    }
}
