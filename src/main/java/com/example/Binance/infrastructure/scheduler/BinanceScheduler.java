package com.example.Binance.infrastructure.scheduler;

import com.example.Binance.service.BinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BinanceScheduler {
    private final BinanceService binanceService;

    @Scheduled(fixedDelayString =  "${binance.poll-interval:PT5M}")
    public void run(){
        binanceService.run();
    }
}
