package com.example.Binance.domin;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Component
public class TrendCalculator {
    private static final int WINDOW_SIZE = 20;
    private static final double FLAT_THRESHOLD_PCT = 0.1;

    public enum Trend { UPWARD, DOWNWARD, FLAT }

    public record TrendResult(
            String symbol,
            Trend trend,
            double slope,       // наклон линии регрессии (цена за одну точку окна)
            double changePct,   // изменение за окно по линии регрессии, %
            int points) {}

    public TrendResult calculate(String symbol, List<TradingPair> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("Prices must not be empty");
        }

        List<TradingPair> ordered = prices.stream()
                .sorted(Comparator.comparing(TradingPair::getFetchedAt))
                .toList();

        int start = Math.max(0, ordered.size() - WINDOW_SIZE);
        List<TradingPair> window = ordered.subList(start, ordered.size());
        int points = window.size();

        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;

        for (int i = 0; i < points; i++) {
            double x = i;
            double y = window.get(i).getPrice().doubleValue();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumXX += x * x;
        }

        double meanY = sumY / points;
        double denominator = points * sumXX - sumX * sumX;
        double slope = denominator == 0
                ? 0
                : (points * sumXY - sumX * sumY) / denominator;

        double predictedChange = slope * (points - 1);
        double changePct = meanY == 0 ? 0 : predictedChange / meanY * 100;

        Trend trend;
        if (Math.abs(changePct) < FLAT_THRESHOLD_PCT) {
            trend = Trend.FLAT;
        } else {
            trend = changePct > 0 ? Trend.UPWARD : Trend.DOWNWARD;
        }

        return new TrendResult(symbol, trend, slope, changePct, points);
    }
}
