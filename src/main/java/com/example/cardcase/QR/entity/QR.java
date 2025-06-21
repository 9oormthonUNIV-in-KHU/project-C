package com.example.cardcase.QR.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class QR {
    @Id @GeneratedValue
    private Long id;

    private String qrUrl;
    private LocalDateTime expireTime;

}
