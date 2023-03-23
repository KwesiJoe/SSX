package io.staxex.api.fxpurchase.models;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.fxpurchase.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    private Long id;
    @ManyToOne
    private Trader trader;
    @ManyToOne
    private LiquidityProvider liquidityProvider;
    private Double amountRequested;
    private Double amountFulfilled;
    private Double exchangeRate;
    private Date dateRequested;
    private Date dateCompleted;
    @Enumerated(EnumType.STRING)
    private Status status;
}
