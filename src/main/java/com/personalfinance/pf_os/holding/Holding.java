package com.personalfinance.pf_os.holding;

import com.personalfinance.pf_os.asset.Asset;
import com.personalfinance.pf_os.portfolio.Portfolio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "holdings", uniqueConstraints = @UniqueConstraint(columnNames = {"portfolio_id", "asset_id"}))

@Getter
@Setter


public class Holding {
    @Id
    @GeneratedValue
    private Long id;

    private UUID publicId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal avgCost;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal realizedGain;

}
