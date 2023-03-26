package io.staxex.api.wallets.repositories;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.wallets.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findAllByTrader(Trader trader);

     Optional<Wallet> findWalletById(Long id);

}
