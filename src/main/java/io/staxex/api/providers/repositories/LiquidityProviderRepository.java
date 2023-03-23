package io.staxex.api.providers.repositories;

import io.staxex.api.providers.models.LiquidityProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiquidityProviderRepository extends JpaRepository<LiquidityProvider, Long> {
}
