package io.staxex.api.orders.repositories;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.orders.models.Order;
import io.staxex.api.providers.models.LiquidityProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByTrader(Trader trader);
    List<Order> findOrdersByTraderAndLiquidityProvider(Trader trader, LiquidityProvider liquidityProvider);
    List<Order> findOrdersByLiquidityProvider(LiquidityProvider liquidityProvider);
}
