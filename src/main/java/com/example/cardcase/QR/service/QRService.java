package com.example.cardcase.QR.service;

import com.example.cardcase.QR.dto.QRResponse;
import com.example.cardcase.QR.entity.QR;
import com.example.cardcase.QR.repository.QRRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QRService {


    private final QRRepository qrRepository;

    public QRResponse generateQrImage(String content) {
        try {
            // 1. QR 코드 생성
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 250, 250);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // 2. QR 객체 저장
            QR qr = new QR();
            qr.setQrUrl(content);
            qr.setExpireTime(LocalDateTime.now().plusMinutes(10));
            qrRepository.save(qr);

            // 3. BufferedImage → Base64 String
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", outputStream);
            String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());

            return new QRResponse(qr.getId(), base64Image, qr.getExpireTime());


        } catch (WriterException | java.io.IOException e) {
            throw new RuntimeException("QR 코드 생성 실패", e);
        }
    }
}
