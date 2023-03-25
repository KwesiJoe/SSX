package io.staxex.api.bankAccountManagement.repositories;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    List<BankAccount> findAllByTrader(Trader trader);
}
