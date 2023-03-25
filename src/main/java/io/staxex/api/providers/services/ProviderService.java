package io.staxex.api.providers.services;

import io.staxex.api.bankAccountManagement.models.BankAccount;
import io.staxex.api.providers.models.LiquidityProvider;
import io.staxex.api.providers.repositories.LiquidityProviderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {
    private final LiquidityProviderRepository liquidityProviderRepository;

    public ProviderService(LiquidityProviderRepository liquidityProviderRepository) {
        this.liquidityProviderRepository = liquidityProviderRepository;
    }

    public LiquidityProvider addProvider(LiquidityProvider liquidityProvider){
        return liquidityProviderRepository.save(liquidityProvider);
    }

    public Optional<LiquidityProvider> getProvider(Long id){
        return liquidityProviderRepository.findById(id);
    }

    public List<LiquidityProvider> getAllProviders(){
        return liquidityProviderRepository.findAll(Sort.by("name"));
    }

    public LiquidityProvider updateProvider(LiquidityProvider liquidityProvider, Long id){
        Optional<LiquidityProvider> provider = liquidityProviderRepository.findById(id);

        if (provider.isPresent()){
            LiquidityProvider providerToUpdate = provider.get();

            providerToUpdate.setName(liquidityProvider.getName());
            providerToUpdate.setEmail(liquidityProvider.getEmail());
            providerToUpdate.setWebsite(liquidityProvider.getWebsite());
            providerToUpdate.setPhoneNumber(liquidityProvider.getPhoneNumber());
            providerToUpdate.setContactPerson(liquidityProvider.getContactPerson());

            return liquidityProviderRepository.save(providerToUpdate);
        }
        else {
            return liquidityProviderRepository.save(liquidityProvider);
        }
    }

    public void deleteProvider(Long id){
        if (liquidityProviderRepository.existsById(id)){
            liquidityProviderRepository.deleteById(id);
        }
    }
}
