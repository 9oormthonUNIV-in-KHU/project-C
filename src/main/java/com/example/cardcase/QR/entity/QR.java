package com.example.cardcase.QR.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
@Getter
@Entity
public class QR {
    @Id @GeneratedValue
    private Long id;
    //QRService에서 바꾸라고 Data 간편하게 씀
    // TODO : 나중에 바꿔야할 수도 있음 문제가 있다면
    private String qrUrl;
    private LocalDateTime expireTime;

}
