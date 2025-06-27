package com.example.cardcase.QR.repository;

import com.example.cardcase.QR.entity.QR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRRepository extends JpaRepository<QR, Long> {

}
