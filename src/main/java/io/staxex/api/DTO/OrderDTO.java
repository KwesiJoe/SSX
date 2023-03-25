package io.staxex.api.DTO;

import io.staxex.api.bankAccountManagement.models.BankAccount;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderDTO {
    private String currencyPair;
    private Long liquidityProvider;
    private BigDecimal amountRequested;
    private BankAccount deliveryAccount;
    private LocalDateTime dateRequested;
    private LocalDateTime dateCompleted;
    private LocalDateTime timeframe;
}
