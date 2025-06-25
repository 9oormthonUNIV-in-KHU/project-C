package com.example.cardcase.QR.controller;

import com.beust.ah.A;
import com.example.cardcase.QR.dto.QRRequest;
import com.example.cardcase.QR.dto.QRResponse;
import com.example.cardcase.QR.entity.QR;
import com.example.cardcase.QR.service.QRService;
import com.example.cardcase.common.apiPayload.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/qr")
public class QRController {


    private final QRService qrService;

    @PostMapping("/generate")
    public ApiResponse<QRResponse> generate(@RequestParam String url) {

        QRResponse qrresponse = qrService.generateQrImage(url);
        return ApiResponse.success(qrresponse);

    }
}
