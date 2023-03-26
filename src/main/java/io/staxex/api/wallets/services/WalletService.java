package io.staxex.api.wallets.services;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.exceptions.WalletNotFoundException;
import io.staxex.api.wallets.models.Wallet;
import io.staxex.api.wallets.repositories.WalletRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public Wallet addWallet(Wallet wallet){
        return walletRepository.save(wallet);
    }

    public Wallet loadWallet(Wallet wallet, BigDecimal amount) throws WalletNotFoundException{
        if (walletRepository.existsById(wallet.getId())) {
            wallet.setBalance(wallet.getBalance().add(amount));
            return walletRepository.save(wallet);
        }
        else{
            throw new WalletNotFoundException("wallet not found");
        }
    }

    public Wallet withDrawFromWallet(Wallet wallet, BigDecimal amount) throws WalletNotFoundException, RuntimeException {
        if (walletRepository.existsById(wallet.getId())) {
            if (wallet.getBalance().subtract(amount).signum() > 0) {
                wallet.setBalance(wallet.getBalance().subtract(amount));
                return walletRepository.save(wallet);
            }
            else {
                throw new RuntimeException("insufficient balance");
            }
        }
        else{
            throw new WalletNotFoundException("wallet not found");
        }
    }

    public Wallet createWallet(Trader trader, String currency){
        Wallet wallet = new Wallet(trader, currency);
        return walletRepository.save(wallet);
    }

    public void deleteWallet(Long id){
        if (walletRepository.existsById(id))
            walletRepository.deleteById(id);
    }

    public Optional<Wallet> getWallet(Long id){
        return walletRepository.findWalletById(id);
    }

    public List<Wallet> getWallets(Trader trader) {
        return walletRepository.findAllByTrader(trader);
    }
}
