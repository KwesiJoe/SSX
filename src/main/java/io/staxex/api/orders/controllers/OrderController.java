package io.staxex.api.orders.controllers;

import io.staxex.api.DTO.OrderDTO;
import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.repositories.TraderRepository;
import io.staxex.api.authentication.services.TokenService;
import io.staxex.api.exceptions.InsufficientFundsException;
import io.staxex.api.orders.models.Order;
import io.staxex.api.orders.services.OrderService;
import io.staxex.api.providers.models.LiquidityProvider;
import io.staxex.api.providers.services.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {
    OrderService orderService;
    TokenService tokenService;

    TraderRepository traderRepository;

    ProviderService providerService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody OrderDTO order) throws InsufficientFundsException {
        String token = Objects.requireNonNull(authorization.substring(7));

        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        LiquidityProvider provider = providerService.getProvider(order.getLiquidityProvider()).get();

        Order newOrder = new Order(trader, order.getCurrencyPair(), provider, order.getAmountRequested(),order.getDeliveryAccount(), order.getTimeframe());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(newOrder));
    }

    @GetMapping()
    public String getOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String token = Objects.requireNonNull(authorization.substring(7));

        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();
        return "all orders";
    }

}
