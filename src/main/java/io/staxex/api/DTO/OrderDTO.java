package io.staxex.api.DTO;

import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.wallets.models.Wallet;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderDTO {
    private String currencyPair;
    private Long liquidityProvider;
    private BigDecimal amountRequested;

    private Long wallet;
    private Long deliveryAccount;
    private LocalDateTime dateRequested;
    private LocalDateTime dateCompleted;
    private LocalDateTime timeframe;
}
