package io.staxex.api.bankAccountManagement.services;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.bankAccountManagement.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount addBankAccount(BankAccount bankAccount){
        return bankAccountRepository.save(bankAccount);
    }

    public List<BankAccount> getAllBankAccounts(Trader trader){
        return bankAccountRepository.findAllByTrader(trader);
    }

    public Optional<BankAccount> getBankAccount(Long id){
        return bankAccountRepository.findById(id);
    }



    public BankAccount updateBankAccountDetails(BankAccount bankAccount, Long id){
        Optional<BankAccount> account = bankAccountRepository.findById(id);

        if (account.isPresent()){
            BankAccount accountToUpdate = account.get();
            accountToUpdate.setAccountNumber(bankAccount.getAccountNumber());
            accountToUpdate.setAccountName(bankAccount.getAccountName());
            accountToUpdate.setTrader(bankAccount.getTrader());
            accountToUpdate.setBankName(bankAccount.getBankName());

            return bankAccountRepository.save(accountToUpdate);
        }
        else {
            return bankAccountRepository.save(bankAccount);
        }

    }

    public void deleteAccount(Long id){
        if (bankAccountRepository.existsById(id)){
            bankAccountRepository.deleteById(id);
        }
    }

//    public List<BankAccount> getAllAccounts(){
//        List<BankAccount> bankAccounts = new ArrayList<>();
//
//        bankAccountRepository.findAllByTrader().get
//
//        return ;
//    }

}
