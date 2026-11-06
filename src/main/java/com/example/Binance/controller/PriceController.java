package com.example.Binance.controller;

import com.example.Binance.DTO.AveragePriceResponse;
import com.example.Binance.DTO.TradingPairPageResponse;
import com.example.Binance.DTO.TradingPairResponse;
import com.example.Binance.DTO.TrendResponse;
import com.example.Binance.service.BinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {
    private final BinanceService binanceService;

    @GetMapping("/latest")
    public ResponseEntity<TradingPairResponse> getLatestPrice() {
        return binanceService.getLatestPrice()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/average")
    public ResponseEntity<AveragePriceResponse> getAveragePrice(
            @RequestParam OffsetDateTime from,
            @RequestParam OffsetDateTime to
    ) {
        if (from.isAfter(to)) {
            return ResponseEntity.badRequest().build();
        }

        return binanceService.getAveragePrice(from, to)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<TradingPairPageResponse> getPrices(
            @RequestParam OffsetDateTime from,
            @RequestParam OffsetDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        if (from.isAfter(to) || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                binanceService.getPrices(from, to, pageable)
        );
    }

    @GetMapping("/trend")
    public ResponseEntity<TrendResponse> getTrend(
            @RequestParam OffsetDateTime from,
            @RequestParam OffsetDateTime to
    ) {
        if (from.isAfter(to)) {
            return ResponseEntity.badRequest().build();
        }

        return binanceService.getTrend(from, to)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
