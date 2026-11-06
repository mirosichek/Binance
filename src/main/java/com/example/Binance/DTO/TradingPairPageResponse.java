package com.example.Binance.DTO;

import com.example.Binance.domin.TradingPair;

import java.util.List;


public class TradingPairPageResponse {
    private List<TradingPairResponse> items;
    private int page;
    private int size;
    private int totalElements;
    private boolean hasNext;

    public TradingPairPageResponse(List<TradingPairResponse> items, int page, int size, int totalElements, boolean hasNext) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.hasNext = hasNext;
    }

    public List<TradingPairResponse> getItems() {
        return items;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public boolean isHasNext() {
        return hasNext;
    }
}
