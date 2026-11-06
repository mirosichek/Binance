package com.example.Binance.DTO;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class BinanceResponse {

    private Integer mins;
    private BigDecimal price;
    private Long closeTime;
    private String symbol;

    public BinanceResponse() {
    }

    public Integer getMins() {
        return mins;
    }

    public void setMins(Integer mins) {
        this.mins = mins;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "BinanceResponse{" +
                "mins=" + mins +
                ", price=" + price +
                ", closeTime=" + closeTime +
                '}';
    }
}