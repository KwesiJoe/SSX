package io.staxex.api.wallets.services;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.wallets.models.Wallet;
import io.staxex.api.wallets.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public Wallet loadWallet(Trader trader, BigDecimal amount){
        Wallet wallet = walletRepository.findWalletByTrader(trader);
        wallet.setBalance(wallet.getBalance().add(amount));

        return  walletRepository.save(wallet);
    }

    public Wallet updateWallet(Trader trader, BigDecimal amount){
        Wallet wallet = walletRepository.findWalletByTrader(trader);
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return  walletRepository.save(wallet);
    }

    public Wallet createWallet(Trader trader){
        Wallet wallet = new Wallet(trader);
        return walletRepository.save(wallet);
    }

    public Wallet getWallet(Trader trader) {
        return walletRepository.findWalletByTrader(trader);
    }
}
