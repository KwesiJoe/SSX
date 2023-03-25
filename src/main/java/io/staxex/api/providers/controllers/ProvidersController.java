package io.staxex.api.providers.controllers;

import io.staxex.api.authentication.repositories.TraderRepository;
import io.staxex.api.authentication.services.TokenService;
import io.staxex.api.providers.models.LiquidityProvider;
import io.staxex.api.providers.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TokenService tokenService;
    @Autowired
    TraderRepository traderRepository;

    public ProvidersController(ProviderService liquidityProviderService) {
        this.liquidityProviderService = liquidityProviderService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<LiquidityProvider> createProvider(@RequestBody LiquidityProvider provider){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(liquidityProviderService.addProvider(provider));
    }

    @GetMapping()
    public List<LiquidityProvider> geAllProviders(){
        return liquidityProviderService.getAllProviders();
    }

    @GetMapping("/{id}")
    public Optional<LiquidityProvider> geAllProviders(@PathVariable Long id){
        System.out.println("+++++++++GOT HERE++++++++++++");
        return liquidityProviderService.getProvider(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<LiquidityProvider> updateProvider(@RequestBody LiquidityProvider liquidityProvider, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(liquidityProviderService.updateProvider(liquidityProvider, id));

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> deleteProvider(@PathVariable Long id){
        liquidityProviderService.deleteProvider(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
