package io.staxex.api.orders.services;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.services.BankAccountService;
import io.staxex.api.orders.models.Order;
import io.staxex.api.orders.repositories.OrderRepository;
import io.staxex.api.wallets.services.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private WalletService walletService;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testGetOrder() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setTrader(new Trader());
        Optional<Order> optionalOrder = Optional.of(order);

        when(orderService.getOrder(orderId)).thenReturn(optionalOrder);
        Optional<Order> foundOrder = orderService.getOrder(orderId);

        assertTrue(foundOrder.isPresent());
        assertEquals(orderId, foundOrder.get().getId());
    }

    @Test
    public void testGetOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());

        when(orderService.getOrders()).thenReturn(orders);
        List<Order> foundOrders = orderService.getOrders();

        assertEquals(orders.size(), foundOrders.size());
    }

    @Test
    public void testGetUserOrders() {
        Trader trader = new Trader();
        List<Order> orders = new ArrayList<>();

        orders.add(new Order());
        orders.add(new Order());

        when(orderService.getUserOrders(trader)).thenReturn(orders);
        List<Order> foundOrders = orderService.getUserOrders(trader);
        assertEquals(orders.size(), foundOrders.size());
    }

//    @Test
//    public void testUpdateOrder() {
//        Long orderId = 1L;
//        Order order = new Order();
//        order.setId(orderId);
//        order.setTrader(new Trader());
//        Optional<Order> optionalOrder = Optional.of(order);
//        when(orderRepository.findById(orderId)).thenReturn(optionalOrder);
//        Order updatedOrder = new Order();
//        updatedOrder.setCurrencyPair("EUR/USD");
//        updatedOrder.setAmountRequested(new BigDecimal("1000"));
//        updatedOrder.setAmountFulfilled(new BigDecimal("0"));
//        updatedOrder.setExchangeRate(new BigDecimal("1.5"));
//        updatedOrder.setDeliveryAccount("123456");
//        updatedOrder.setTimeframe(30);
//        updatedOrder.setStatus(Status.PENDING);
//        orderService.updateOrder(orderId, updatedOrder);
//        verify(orderRepository).save(order);
//
//        assertEquals(updatedOrder.getCurrencyPair(), order.getCurrencyPair());
//        assertEquals(updatedOrder.getAmountRequested(), order.getAmountRequested());
//        assertEquals(updatedOrder.getAmountFulfilled(), order.getAmountFulfilled());
//        assertEquals(updatedOrder.getExchangeRate(), order.getExchangeRate());
//        assertEquals(updatedOrder.getDeliveryAccount(), order.getDeliveryAccount());
//        assertEquals(updatedOrder.getTimeframe(), order.getTimeframe());
//        assertEquals(updatedOrder.getStatus(), order.getStatus());
//    }

//    @Test
//    void testCreateOrder_withSufficientFunds_shouldCreateOrder() throws InsufficientFundsException {
//        // Given
//        Trader trader = new Trader();
//        LiquidityProvider liquidityProvider = new LiquidityProvider();
//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setId(1L);
//        Order order = new Order();
//        order.setTrader(trader);
//        order.setCurrencyPair("EUR/USD");
//        order.setLiquidityProvider(liquidityProvider);
//        order.setAmountRequested(new BigDecimal("1000"));
//        order.setAmountFulfilled(new BigDecimal("0"));
//        order.setExchangeRate(new BigDecimal("1.5"));
//        order.setDeliveryAccount("123456");
//        order.setTimeframe(30);
//        order.setStatus(Status.OPEN);
//        when(walletService.getWallet(any(Trader.class))).thenReturn(new Wallet(trader));
//        when(bankAccountService.getBankAccount(1L)).thenReturn(Optional.of(bankAccount));
//        when(orderRepository.save(any(Order.class))).thenReturn(order);
//
//        // When
//        Order createdOrder = orderService.createOrder(order);
//
//        // Then
//        assertEquals(Status.OPEN, createdOrder.getStatus());
//    }
}
