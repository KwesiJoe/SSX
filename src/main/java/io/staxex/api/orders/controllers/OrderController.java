package io.staxex.api.orders.controllers;

import io.staxex.api.DTO.OrderDTO;
import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.repositories.TraderRepository;
import io.staxex.api.authentication.services.TokenService;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.bankAccountManagement.services.BankAccountService;
import io.staxex.api.exceptions.InsufficientFundsException;
import io.staxex.api.orders.models.Order;
import io.staxex.api.orders.services.OrderService;
import io.staxex.api.providers.models.LiquidityProvider;
import io.staxex.api.providers.services.ProviderService;
import io.staxex.api.wallets.models.Wallet;
import io.staxex.api.wallets.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    TokenService tokenService;
    @Autowired
    TraderRepository traderRepository;

    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    ProviderService providerService;
    @Autowired
    private WalletService walletService;


    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody OrderDTO order) throws InsufficientFundsException {
        String token = Objects.requireNonNull(authorization.substring(7));

        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        Wallet wallet = walletService.getWallet(order.getWallet()).get();
        LiquidityProvider provider = providerService.getProvider(order.getLiquidityProvider()).get();
        BankAccount deliveryAccount = bankAccountService.getBankAccount(order.getDeliveryAccount()).get();

        Order newOrder = new Order(trader, order.getCurrencyPair(), provider, order.getAmountRequested(),deliveryAccount, order.getTimeframe(), wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(newOrder));
    }

    @GetMapping("/myorders")
    public List<Order> getOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String token = Objects.requireNonNull(authorization.substring(7));

        String email = tokenService.getEmailFromJwtToken(token);
        Trader trader = traderRepository.findByEmail(email).get();

        return orderService.getUserOrders(trader);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@RequestBody Order order, @PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrder(id, order));
    }

}
