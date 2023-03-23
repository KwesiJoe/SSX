package io.staxex.api.orders.models;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.orders.enums.Status;
import io.staxex.api.providers.models.LiquidityProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
