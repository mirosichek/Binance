package com.example.Binance.DTO;

import com.example.Binance.domin.TrendCalculator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class TrendResponse {
    private String symbol;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    private TrendCalculator.Trend trend;

    public TrendResponse(
            String symbol,
            LocalDate startDate,
            LocalDate endDate,
            TrendCalculator.Trend trend
    ) {
        this.symbol = symbol;
        this.startDate = startDate;
        this.endDate = endDate;
        this.trend = trend;
    }

    public String getSymbol() {
        return symbol;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public TrendCalculator.Trend getTrend() {
        return trend;
    }
}
