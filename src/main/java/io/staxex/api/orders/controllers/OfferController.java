package io.staxex.api.orders.controllers;

import io.staxex.api.orders.services.MarketDataService;
import io.staxex.api.orders.services.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    MarketDataService marketDataService;
    @GetMapping
    public ResponseEntity<Flux<Offer>> getOffers(){
        return ResponseEntity.status(HttpStatus.OK).body(marketDataService.getOffers());
    }
}
