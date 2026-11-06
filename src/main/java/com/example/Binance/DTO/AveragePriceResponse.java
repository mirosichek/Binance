package com.example.Binance.DTO;

import java.math.BigDecimal;

public class AveragePriceResponse {
    private BigDecimal averagePrice;

    public AveragePriceResponse(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }
}
