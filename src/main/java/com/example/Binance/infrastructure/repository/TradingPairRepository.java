package com.example.Binance.infrastructure.repository;

import com.example.Binance.domin.TradingPair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


public interface TradingPairRepository extends JpaRepository<TradingPair, Long> {

    Optional<TradingPair> findFirstByOrderByFetchedAtDesc();
    List<TradingPair> findTop20ByFetchedAtBetweenOrderByFetchedAtDesc(
            OffsetDateTime from,
            OffsetDateTime to
    );
    List<TradingPair> findByFetchedAtBetween(
            OffsetDateTime from,
            OffsetDateTime to,
            Pageable pageable
    );

    @Query("""
    SELECT AVG(t.price)
    FROM TradingPair t
    WHERE t.fetchedAt BETWEEN :from AND :to
""")
    BigDecimal findAveragePrice(
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to
    );

    @Query("""
    SELECT t
    FROM TradingPair t
    WHERE t.fetchedAt BETWEEN :from AND :to
    """)
    Page<TradingPair> findByDateRange(
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to,
            Pageable pageable
    );
}
