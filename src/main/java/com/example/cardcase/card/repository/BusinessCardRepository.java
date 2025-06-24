package com.example.cardcase.card.repository;

import com.example.cardcase.card.entity.BusinessCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessCardRepository extends JpaRepository<BusinessCard, Long> {
}
