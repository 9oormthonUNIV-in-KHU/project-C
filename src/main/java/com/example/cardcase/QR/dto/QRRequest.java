package com.example.cardcase.QR.dto;

import java.time.LocalDateTime;

public record QRRequest (
        String url, int expireMinutes
){

}
