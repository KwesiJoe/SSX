package io.staxex.api.orders.services;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.services.BankAccountService;
import io.staxex.api.exceptions.InsufficientFundsException;
import io.staxex.api.orders.enums.Status;
import io.staxex.api.orders.models.Order;
import io.staxex.api.orders.payloads.OrderRequestPayload;
import io.staxex.api.orders.payloads.OrderResponsePayload;
import io.staxex.api.orders.repositories.OrderRepository;
import io.staxex.api.wallets.services.WalletService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final String DEFAULTPAYMENTMETHOD = "bank transfer";

    WalletService walletService;

    BankAccountService bankAccountService;

    private final String DEFAULTDELIVERYMETHOD = "bank transfer";

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    /*
    getOrders
    getOrder
    makeOrder
     */

    WebClient webClient;

    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getUserOrders(Trader trader) {
        return orderRepository.findOrdersByTrader(trader);
    }

    public Order createOrder(Order order) throws InsufficientFundsException {
        BigDecimal availableFunds = walletService.getWallet(order.getTrader())
                .getBalance();
        if (availableFunds.compareTo(order.getAmountRequested()) < 0) {
            throw new InsufficientFundsException("Not enough funds in the wallet to place this order.");
        }

        Order newOrder = orderRepository.save(order);

        OrderResponsePayload response = makeOrder(newOrder);
        if (response != null) {
            newOrder.setStatus(response.getStatus());
        } else {
            newOrder.setStatus(Status.FAILED);
        }
        orderRepository.save(newOrder);

        return newOrder;
    }

    private OrderResponsePayload makeOrder(Order order) {

        OrderRequestPayload orderRequestPayload = new OrderRequestPayload(
                order.getCurrencyPair(),
                DEFAULTPAYMENTMETHOD,
                bankAccountService.getBankAccount(1L).get(),
                DEFAULTDELIVERYMETHOD,
                order.getDeliveryAccount(),
                order.getTimeframe()
        );

        try {
            return webClient
                    .post()
                    .uri(order.getLiquidityProvider().getApiUrl())
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(orderRequestPayload), OrderRequestPayload.class)
                    .header("Authorization", "Bearer " + order.getLiquidityProvider().getApiToken())
                    .retrieve()
                    .bodyToMono(OrderResponsePayload.class)
                    .block();
        } catch (WebClientResponseException | WebClientRequestException e) {
            return null;
        }

    }
}
