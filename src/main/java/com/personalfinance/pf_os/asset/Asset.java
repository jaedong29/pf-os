package com.personalfinance.pf_os.asset;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "assets", uniqueConstraints = @UniqueConstraint(columnNames = {"symbol", "type"}))
@Getter
@Setter

public class Asset {
    @Id
    @GeneratedValue
    private Long id;

    private UUID publicId = UUID.randomUUID();

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    @Column(nullable = true)
    private String market;

    @Column(nullable = false)
    private int decimalPlaces;
}
