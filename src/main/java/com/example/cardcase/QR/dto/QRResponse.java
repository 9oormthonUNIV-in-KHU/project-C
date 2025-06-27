package com.example.cardcase.QR.dto;

import java.time.LocalDateTime;

public record QRResponse (Long id,
                          String qrUrl,
                          LocalDateTime expireTime){}
