package com.personalfinance.pf_os.holding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface HoldingRepository extends JpaRepository<Holding, Long> {

    @Modifying
    @Query("UPDATE Holding h SET " +
            "h.avgCost = (h.avgCost * h.quantity + :price * :qty) / (h.quantity + :qty), " +
            "h.quantity = h.quantity + :qty " +
            "WHERE h.portfolio.id = :portfolioId AND h.asset.id = :assetId")
    int applyBuy(@Param("portfolioId") Long portfolioId,
                 @Param("assetId") Long assetId,
                 @Param("price") BigDecimal price,
                 @Param("qty") BigDecimal qty);

    @Modifying
    @Query("UPDATE Holding h SET " +
            "h.realizedGain = h.realizedGain + (:price - h.avgCost) * :qty, " +
            "h.quantity = h.quantity - :qty " +
            "WHERE h.portfolio.id = :portfolioId AND h.asset.id = :assetId AND h.quantity >= :qty")
    int applySell(@Param("portfolioId") Long portfolioId,
                  @Param("assetId") Long assetId,
                  @Param("price") BigDecimal price,
                  @Param("qty") BigDecimal qty);

}