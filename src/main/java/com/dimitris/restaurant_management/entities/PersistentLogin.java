package com.dimitris.restaurant_management.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogin {

    @Column(length = 64,nullable = false)
    private String username;

    @Id
    @Column(length = 64)
    private String series;

    @Column(length = 64, nullable = false)
    private String token;

    @Column(name = "last_used", nullable = false)
    private LocalDateTime timestamp;
}
