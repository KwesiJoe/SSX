package io.staxex.api.orders.payloads;

import io.staxex.api.bankAccountManagement.models.BankAccount;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRequestPayload {
    String currencyPair;
//    double exchangeRate;
    String paymentMethod;
    BankAccount paymentAccount;
    String deliveryMethod;
    BankAccount deliveryAccount;
    LocalDateTime timeframe;

    public OrderRequestPayload(String currencyPair, String paymentMethod, BankAccount paymentAccount, String deliveryMethod, BankAccount deliveryAccount, LocalDateTime timeframe) {
        this.currencyPair = currencyPair;
        this.paymentMethod = paymentMethod;
        this.paymentAccount = paymentAccount;
        this.deliveryMethod = deliveryMethod;
        this.deliveryAccount = deliveryAccount;
        this.timeframe = timeframe;
    }
}
