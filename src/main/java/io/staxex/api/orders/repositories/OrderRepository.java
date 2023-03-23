package io.staxex.api.orders.repositories;

import io.staxex.api.orders.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
