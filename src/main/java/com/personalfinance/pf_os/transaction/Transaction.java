package com.personalfinance.pf_os.transaction;

import com.personalfinance.pf_os.asset.Asset;
import com.personalfinance.pf_os.portfolio.Portfolio;
import com.personalfinance.pf_os.currency.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter

public class Transaction {

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

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal price;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal quantity;

    @Column(precision = 20, scale = 8)
    private BigDecimal fxRateSnapshot;

    @Column(nullable = false)
    private String exchange;

    @Column(nullable = false)
    private LocalDateTime executedAt;


}
