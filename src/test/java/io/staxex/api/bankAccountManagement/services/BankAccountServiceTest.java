package io.staxex.api.bankAccountManagement.services;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.bankAccountManagement.repositories.BankAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BankAccountServiceTest {

    private BankAccountService bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bankAccountService = new BankAccountService(bankAccountRepository);
    }

    @Test
    public void testAddBankAccount() {
        // Setup
        BankAccount bankAccount = new BankAccount(new Trader(), "accountName", "accountNumber", "bankName");
        Mockito.when(bankAccountRepository.save(Mockito.any(BankAccount.class))).thenReturn(bankAccount);

        // Exercise
        BankAccount savedBankAccount = bankAccountService.addBankAccount(bankAccount);

        // Verify
        Assertions.assertEquals(bankAccount, savedBankAccount);
    }

    @Test
    public void testGetAllBankAccounts() {
        // Setup
        Trader trader = new Trader();
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(new BankAccount(trader, "accountName1", "accountNumber1", "bankName1"));
        bankAccounts.add(new BankAccount(trader, "accountName2", "accountNumber2", "bankName2"));
        Mockito.when(bankAccountRepository.findAllByTrader(trader)).thenReturn(bankAccounts);

        // Exercise
        List<BankAccount> retrievedBankAccounts = bankAccountService.getAllBankAccounts(trader);

        // Verify
        Assertions.assertEquals(bankAccounts, retrievedBankAccounts);
    }

    @Test
    public void testGetBankAccount() {
        // Setup
        BankAccount bankAccount = new BankAccount(new Trader(), "accountName", "accountNumber", "bankName");
        Mockito.when(bankAccountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bankAccount));

        // Exercise
        Optional<BankAccount> retrievedBankAccount = bankAccountService.getBankAccount(1L);

        // Verify
        Assertions.assertEquals(Optional.of(bankAccount), retrievedBankAccount);
    }

    @Test
    public void testUpdateBankAccountDetails() {
        // Setup
        BankAccount bankAccount = new BankAccount(new Trader(), "accountName", "accountNumber", "bankName");
        Mockito.when(bankAccountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bankAccount));
        Mockito.when(bankAccountRepository.save(Mockito.any(BankAccount.class))).thenReturn(bankAccount);

        // Exercise
        BankAccount updatedBankAccount = bankAccountService.updateBankAccountDetails(
                new BankAccount(new Trader(), "updatedBankName", "updatedAccountName", "updatedAccountNumber"), 1L);

        // Verify
        Assertions.assertEquals("updatedBankName", updatedBankAccount.getBankName());
        Assertions.assertEquals("updatedAccountName", updatedBankAccount.getAccountName());
        Assertions.assertEquals("updatedAccountNumber", updatedBankAccount.getAccountNumber());

    }

    @Test
    public void testDeleteAccount() {
        // Setup
        Long accountId = 1L;
        Mockito.when(bankAccountRepository.existsById(accountId)).thenReturn(true);

        // Exercise
        bankAccountService.deleteAccount(accountId);

        // Verify
        Mockito.verify(bankAccountRepository, Mockito.times(1)).deleteById(accountId);
    }
}
