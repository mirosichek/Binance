package com.example.Binance.infrastructure.client;

import com.example.Binance.DTO.BinanceResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BinanceClient {
    private final RestClient restClient;
    private static final String BASE_URL = "https://api.binance.com";

    public BinanceClient(RestClient.Builder builder) {
        this.restClient = builder.
                baseUrl(BASE_URL).
                build();
    }

    public BinanceResponse getPrice(String symbol){
        BinanceResponse binanceResponse = new BinanceResponse();
        binanceResponse =restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/avgPrice")
                        .queryParam("symbol", symbol)
                        .build())
                .retrieve()
                .body(BinanceResponse.class);
        binanceResponse.setSymbol(symbol);
        System.out.println(binanceResponse);
        return binanceResponse;
    }
}
