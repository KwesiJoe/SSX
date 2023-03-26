package io.staxex.api.providers.services;

import io.staxex.api.providers.models.LiquidityProvider;
import io.staxex.api.providers.repositories.LiquidityProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ProviderServiceTest {

    private LiquidityProviderRepository liquidityProviderRepository;
    private ProviderService providerService;

    @BeforeEach
    void setUp() {
        liquidityProviderRepository = Mockito.mock(LiquidityProviderRepository.class);
        providerService = new ProviderService(liquidityProviderRepository);
    }

    @Test
    void addProvider_shouldAddProvider() {
        LiquidityProvider liquidityProvider = new LiquidityProvider("Acme Inc.", "acme@example.com",
                "https://www.acme.com", "555-1234", "Jane Doe");
        when(liquidityProviderRepository.save(liquidityProvider)).thenReturn(liquidityProvider);

        LiquidityProvider addedProvider = providerService.addProvider(liquidityProvider);

        assertEquals(liquidityProvider, addedProvider);
        Mockito.verify(liquidityProviderRepository, Mockito.times(1)).save(liquidityProvider);
    }

    @Test
    void getProvider_shouldReturnProviderIfExists() {
        Long id = 1L;
        LiquidityProvider liquidityProvider = new LiquidityProvider("Acme Inc.", "acme@example.com",
                "https://www.acme.com", "555-1234", "Jane Doe");
        when(liquidityProviderRepository.findById(id)).thenReturn(Optional.of(liquidityProvider));

        Optional<LiquidityProvider> foundProvider = providerService.getProvider(id);

        assertTrue(foundProvider.isPresent());
        assertEquals(liquidityProvider, foundProvider.get());
    }

    @Test
    void getProvider_shouldReturnEmptyOptionalIfProviderDoesNotExist() {
        Long id = 1L;
        when(liquidityProviderRepository.findById(id)).thenReturn(Optional.empty());

        Optional<LiquidityProvider> foundProvider = providerService.getProvider(id);

        assertTrue(foundProvider.isEmpty());
    }

    @Test
    void getAllProviders_shouldReturnAllProvidersSortedByName() {
        List<LiquidityProvider> liquidityProviders = new ArrayList<>();
        liquidityProviders.add(new LiquidityProvider("Acme Inc.", "acme@example.com",
                "https://www.acme.com", "555-1234", "Jane Doe"));
        liquidityProviders.add(new LiquidityProvider("Beta Corp.", "beta@example.com",
                "https://www.beta.com", "555-5678", "John Smith"));
        when(liquidityProviderRepository.findAll(Sort.by("name"))).thenReturn(liquidityProviders);

        List<LiquidityProvider> foundProviders = providerService.getAllProviders();

        assertEquals(liquidityProviders, foundProviders);
    }

    @Test
    void updateProvider_shouldUpdateProviderIfExists() {
        Long id = 1L;
        LiquidityProvider liquidityProvider = new LiquidityProvider("Acme Inc.", "acme@example.com",
                "https://www.acme.com", "555-1234", "Jane Doe");
        LiquidityProvider existingProvider = new LiquidityProvider("Old Inc.", "old@example.com",
                "https://www.old.com", "555-5678", "Old Person");
        when(liquidityProviderRepository.findById(id)).thenReturn(Optional.of(existingProvider));
        when(liquidityProviderRepository.save(existingProvider)).thenReturn(existingProvider);

        LiquidityProvider updatedProvider = providerService.updateProvider(liquidityProvider, id);

        assertEquals(existingProvider, updatedProvider);
    }
}
