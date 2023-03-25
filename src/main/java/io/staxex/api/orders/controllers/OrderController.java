package io.staxex.api.orders.controllers;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.repositories.TraderRepository;
import io.staxex.api.authentication.services.TokenService;
import io.staxex.api.exceptions.InsufficientFundsException;
import io.staxex.api.orders.DTO.OrderDTO;
import io.staxex.api.orders.models.Order;
import io.staxex.api.orders.services.OrderService;
import io.staxex.api.providers.models.LiquidityProvider;
import io.staxex.api.providers.services.ProviderService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private OrderService orderService;
    private TokenService tokenService;

    TraderRepository traderRepository;

    ProviderService providerService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO order, @RequestHeader HttpHeaders header) throws InsufficientFundsException {
        String token = Objects.requireNonNull(header.getFirst(HttpHeaders.AUTHORIZATION)).substring(7);
        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        LiquidityProvider provider = providerService.getProvider(order.getLiquidityProvider()).get();

        Order newOrder = new Order(trader, order.getCurrencyPair(), provider, order.getAmountRequested(),order.getDeliveryAccount(), order.getTimeframe());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(newOrder));
    }

    @GetMapping("/")
    public String getOrders(){
        return "all orders";
    }

}
