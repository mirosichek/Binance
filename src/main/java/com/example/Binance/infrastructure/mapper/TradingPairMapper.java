package com.example.Binance.infrastructure.mapper;

import com.example.Binance.DTO.TradingPairResponse;
import com.example.Binance.domin.TradingPair;
import com.example.Binance.DTO.BinanceResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class TradingPairMapper {
    public TradingPair responseToTradingPair(BinanceResponse binanceResponse){
        String symbol= binanceResponse.getSymbol();
        BigDecimal price = binanceResponse.getPrice();
        OffsetDateTime fetchedAt = OffsetDateTime.now(ZoneOffset.UTC);
        TradingPair tradingPair = new TradingPair(symbol, price, fetchedAt );
        return tradingPair;
    }

    public TradingPairResponse tradingPairToResponse(TradingPair tradingPair){
        TradingPairResponse tradingPairResponse = new TradingPairResponse(
                tradingPair.getSymbol(),
                tradingPair.getPrice(),
                tradingPair.getFetchedAt().toInstant());
        return tradingPairResponse;
    }
}
