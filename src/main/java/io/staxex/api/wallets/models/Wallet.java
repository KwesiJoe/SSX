package io.staxex.api.wallets.models;

import io.staxex.api.authentication.models.Trader;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

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

    public Wallet(Trader trader) {
        this.trader = trader;
        this.balance = BigDecimal.valueOf(0);
    }
}