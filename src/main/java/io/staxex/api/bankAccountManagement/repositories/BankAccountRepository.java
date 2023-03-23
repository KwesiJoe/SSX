package io.staxex.api.bankAccountManagement.repositories;

import io.staxex.api.bankAccountManagement.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
