package io.staxex.api.orders.DTO;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.orders.enums.Status;
import io.staxex.api.providers.models.LiquidityProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class OrderDTO {
    private String currencyPair;
    private Long liquidityProvider;
    private BigDecimal amountRequested;
    private BankAccount deliveryAccount;
    private LocalDateTime dateRequested;
    private LocalDateTime dateCompleted;
    private Duration timeframe;
}
