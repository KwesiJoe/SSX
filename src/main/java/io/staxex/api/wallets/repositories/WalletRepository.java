package io.staxex.api.wallets.repositories;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.wallets.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByTrader(Trader trader);
}
