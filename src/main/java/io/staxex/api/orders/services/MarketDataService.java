package io.staxex.api.orders.services;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class MarketDataService {
    private final WebClient webClient;

    final String FIXApiKey = "1PLACEHOLDER";

    public MarketDataService() {
        this.webClient = WebClient.create("https://mocki.io");
    }

    public Flux<Offer> getOffers(){
        return webClient.get()
                .uri("/v1/f5c36d37-75f4-4098-933b-06920f1eb561")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Offer.class);
    }
}
