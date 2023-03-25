package io.staxex.api.orders.services;

import lombok.Data;

@Data
public class Offer {
    String currencyPair;
    Double exchangeRate;
    Integer MaxVolume;
}
