package com.example.Binance.DTO;

import java.math.BigDecimal;
import java.time.Instant;

public class TradingPairResponse {
    private String symbol;
    private BigDecimal price;
    private Instant fetchedAt;

    public TradingPairResponse(String symbol, BigDecimal price, Instant fetchedAt) {
        this.symbol = symbol;
        this.price = price;
        this.fetchedAt = fetchedAt;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setFetchedAt(Instant fetchedAt) {
        this.fetchedAt = fetchedAt;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getFetchedAt() {
        return fetchedAt;
    }
}
