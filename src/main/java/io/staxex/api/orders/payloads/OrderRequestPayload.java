package io.staxex.api.orders.payloads;

import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.wallets.models.Wallet;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRequestPayload {
    String currencyPair;
//    double exchangeRate;
    String paymentMethod;
    Wallet wallet;
    String deliveryMethod;
    BankAccount deliveryAccount;
    LocalDateTime timeframe;

    public OrderRequestPayload(String currencyPair, String paymentMethod, Wallet wallet, String deliveryMethod, BankAccount deliveryAccount, LocalDateTime timeframe) {
        this.currencyPair = currencyPair;
        this.paymentMethod = paymentMethod;
        this.wallet = wallet;
        this.deliveryMethod = deliveryMethod;
        this.deliveryAccount = deliveryAccount;
        this.timeframe = timeframe;
    }
}
