package com.example.Binance.service;

import com.example.Binance.DTO.AveragePriceResponse;
import com.example.Binance.DTO.TradingPairPageResponse;
import com.example.Binance.DTO.TradingPairResponse;
import com.example.Binance.DTO.TrendResponse;
import com.example.Binance.domin.TradingPair;
import com.example.Binance.domin.TrendCalculator;
import com.example.Binance.infrastructure.client.BinanceClient;
import com.example.Binance.DTO.BinanceResponse;
import com.example.Binance.infrastructure.mapper.TradingPairMapper;
import com.example.Binance.infrastructure.repository.TradingPairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BinanceService {
    private final BinanceClient binanceClient;
    private final TradingPairMapper tradingPairMapper;
    private final TradingPairRepository tradingPairRepository;
    private final TrendCalculator trendCalculator;

    public void run() {
        BinanceResponse binanceResponse =binanceClient.getPrice("BTCUSDT");
        TradingPair tradingPair =tradingPairMapper.responseToTradingPair(binanceResponse);
        tradingPairRepository.save(tradingPair);
    }

    public Optional<TradingPairResponse> getLatestPrice() {
        return tradingPairRepository.findFirstByOrderByFetchedAtDesc()
                .map(tradingPairMapper::tradingPairToResponse);
    }

    public Optional<AveragePriceResponse> getAveragePrice(
            OffsetDateTime from,
            OffsetDateTime to
    ){
        return Optional.ofNullable(
                tradingPairRepository.findAveragePrice(from, to)
        ).map(AveragePriceResponse::new);
    }

    public TradingPairPageResponse getPrices(
            OffsetDateTime from,
            OffsetDateTime to,
            Pageable pageable
    ) {
        Page<TradingPair> result =
                tradingPairRepository.findByDateRange(from, to, pageable);

        List<TradingPairResponse> items = result.getContent()
                .stream()
                .map(tradingPairMapper::tradingPairToResponse)
                .toList();

        return new TradingPairPageResponse(
                items,
                result.getNumber(),
                result.getSize(),
                (int) result.getTotalElements(),
                result.hasNext()
        );
    }

    public Optional<TrendResponse> getTrend(
            OffsetDateTime from,
            OffsetDateTime to
    ) {
        List<TradingPair> prices =
                tradingPairRepository.findTop20ByFetchedAtBetweenOrderByFetchedAtDesc(from, to);

        if (prices.isEmpty()) {
            return Optional.empty();
        }

        String symbol = prices.getFirst().getSymbol();
        TrendCalculator.TrendResult result = trendCalculator.calculate(symbol, prices);

        return Optional.of(new TrendResponse(
                result.symbol(),
                from.toLocalDate(),
                to.toLocalDate(),
                result.trend()
        ));
    }

}
