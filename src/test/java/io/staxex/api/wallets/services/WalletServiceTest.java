package io.staxex.api.wallets.services;

import static org.junit.jupiter.api.Assertions.*;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.wallets.models.Wallet;
import io.staxex.api.wallets.repositories.WalletRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class WalletServiceTest {

    private WalletRepository walletRepository;
    private WalletService walletService;
    private Trader trader;

    @BeforeEach
    void setUp() {
        trader = new Trader("John", "Doe", "john.doe@mail.com","hisPassword");
        walletRepository = Mockito.mock(WalletRepository.class);
        walletService = new WalletService(walletRepository);
    }

    @Test
    void loadWallet_shouldIncreaseBalanceAndSave() {
        Wallet wallet = new Wallet(trader);
        BigDecimal amount = BigDecimal.valueOf(100.0);
        when(walletRepository.findWalletByTrader(trader)).thenReturn(wallet);
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet loadedWallet = walletService.loadWallet(trader, amount);

        assertEquals(wallet, loadedWallet);
        assertEquals(amount, loadedWallet.getBalance());
        Mockito.verify(walletRepository, Mockito.times(1)).save(wallet);
    }

    @Test
    void updateWallet_shouldDecreaseBalanceAndSave() {
        Wallet wallet = new Wallet(trader);
        BigDecimal amount = BigDecimal.valueOf(50.0);
        wallet.setBalance(BigDecimal.valueOf(100.0));
        when(walletRepository.findWalletByTrader(trader)).thenReturn(wallet);
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet = walletService.updateWallet(trader, amount);

        assertEquals(wallet, updatedWallet);
        assertEquals(BigDecimal.valueOf(50.0), updatedWallet.getBalance());
        Mockito.verify(walletRepository, Mockito.times(1)).save(wallet);
    }

    @Test
    void createWallet_shouldCreateAndSaveNewWallet() {
        when(walletRepository.save(Mockito.any(Wallet.class))).thenAnswer(i -> i.getArguments()[0]);

        Wallet createdWallet = walletService.createWallet(trader);

        assertNotNull(createdWallet);
        assertEquals(trader, createdWallet.getTrader());
        assertEquals(BigDecimal.ZERO, createdWallet.getBalance());
        Mockito.verify(walletRepository, Mockito.times(1)).save(createdWallet);
    }

    @Test
    void getWallet_shouldReturnWalletIfExists() {
        Wallet wallet = new Wallet(trader);
        when(walletRepository.findWalletByTrader(trader)).thenReturn(wallet);

        Wallet foundWallet = walletService.getWallet(trader);

        assertEquals(wallet, foundWallet);
    }

    @Test
    void getWallet_shouldReturnNullIfWalletDoesNotExist() {
        when(walletRepository.findWalletByTrader(trader)).thenReturn(null);

        Wallet foundWallet = walletService.getWallet(trader);

        assertNull(foundWallet);
    }
}
