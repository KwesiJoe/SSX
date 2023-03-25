package io.staxex.api.providers.controllers;

import io.staxex.api.providers.models.LiquidityProvider;
import io.staxex.api.providers.services.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/providers")
public class ProvidersController {

    private ProviderService liquidityProviderService;

    public ProvidersController(ProviderService liquidityProviderService) {
        this.liquidityProviderService = liquidityProviderService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LiquidityProvider> createProvider(@RequestBody LiquidityProvider provider){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(liquidityProviderService.addProvider(provider));
    }

    @GetMapping("/list")
    public List<LiquidityProvider> geAllProviders(){
        return liquidityProviderService.getAllProviders();
    }

    @GetMapping("/{id}")
    public Optional<LiquidityProvider> geAllProviders(@PathVariable Long id){
        return liquidityProviderService.getProvider(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LiquidityProvider> updateProvider(@RequestBody LiquidityProvider liquidityProvider, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(liquidityProviderService.updateProvider(liquidityProvider, id));

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProvider(@PathVariable Long id){
        liquidityProviderService.deleteProvider(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
