package io.staxex.api.orders.models;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.orders.enums.Status;
import io.staxex.api.providers.models.LiquidityProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    @ManyToOne
    private Trader trader;

    private String currencyPair;
    @ManyToOne
    private LiquidityProvider liquidityProvider;
    @Column(scale = 2, precision = 10)
    private BigDecimal amountRequested;
    @Column(scale = 2, precision = 10)
    private BigDecimal amountFulfilled;
    private Double exchangeRate;
    @OneToOne
    private BankAccount deliveryAccount;
    @CreationTimestamp
    private LocalDateTime dateRequested;
    private LocalDateTime dateCompleted;

    private LocalDateTime timeframe;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Order(Trader trader,
                 String currencyPair,
                 LiquidityProvider liquidityProvider,
                 BigDecimal amountRequested,
                 BankAccount deliveryAccount,
                 LocalDateTime timeframe
    ) {
        this.trader = trader;
        this.currencyPair = currencyPair;
        this.liquidityProvider = liquidityProvider;
        this.amountRequested = amountRequested;
        this.deliveryAccount = deliveryAccount;
        this.timeframe = timeframe;
    }

    public Boolean isElapsed(){
//        Long timeSinceRequest= this.dateRequested.LocalDateTime.now());
//        this.timeframe.t
        return (this.timeframe.isAfter(LocalDateTime.now()));
    }

//    if the time elapses, send email to the user asking if she would like to cancell order
}
