package com.example.cardcase.card.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class BusinessCard {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String company_name;
    private String department;
    private String phone_number;
    private String email;
    private String company_email;
    private String company_location;
    private String sns;
    private String company_number;

}
