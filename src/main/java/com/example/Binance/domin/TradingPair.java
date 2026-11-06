package com.example.Binance.domin;

import jakarta.persistence.Entity;//каждый созданный объект этого класса будет строкой в этой таблице
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Entity
@Table(name = "trading_pair")
public class TradingPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private BigDecimal price;
    private OffsetDateTime fetchedAt;

    public TradingPair() {
    }

    public TradingPair(String symbol, BigDecimal price, OffsetDateTime fetchedAt) {
        this.symbol = symbol;
        this.price = price;
        this.fetchedAt = fetchedAt;
    }

    public String getSymbol() {
        return symbol;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OffsetDateTime getFetchedAt() {
        return fetchedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setFetchedAt(OffsetDateTime fetchedAt) {
        this.fetchedAt = fetchedAt;
    }

    @Override
    public String toString() {
        return "TradingPair{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                ", fetchedAt=" + fetchedAt +
                '}';
    }
}
