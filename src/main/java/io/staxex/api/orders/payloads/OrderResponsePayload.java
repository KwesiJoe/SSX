package io.staxex.api.orders.payloads;

import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.orders.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderResponsePayload {
    String requestId;
    Status status;
    BigDecimal amountFulfilled;
    BigDecimal amountReceived;
    double exchangeRate;
    String  transactionId;
    Date transactionDate;
    String paymentMethod;
    BankAccount paymentAccount;
    String deliveryMethod;
    BankAccount deliveryAccount;
}
