package com.personalfinance.pf_os.currency;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "currencies")
@Getter
@Setter

public class Currency {
    @Id
    @GeneratedValue
    private Long id;

    private UUID publicId = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int decimalPlaces;
}
