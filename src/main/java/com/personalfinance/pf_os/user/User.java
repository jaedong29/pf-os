package com.personalfinance.pf_os.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="users")
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue
    private Long id;

    private UUID publicId = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name="password_hash",nullable = false)
    private String passwordHash;

    private LocalDateTime createdAt = LocalDateTime.now();

}
