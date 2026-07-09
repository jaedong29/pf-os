package com.personalfinance.pf_os.portfolio;

import com.personalfinance.pf_os.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "portfolios")
@Getter
@Setter

public class Portfolio {
    @Id
    @GeneratedValue
    private Long id;

    private UUID publicId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;
}
