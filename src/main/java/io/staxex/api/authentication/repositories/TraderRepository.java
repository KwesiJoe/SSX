package io.staxex.api.authentication.repositories;

import io.staxex.api.authentication.models.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TraderRepository extends JpaRepository<Trader, Long> {
    boolean existsByEmail(String email);
    Optional<Trader> findByEmail(String email);
}
