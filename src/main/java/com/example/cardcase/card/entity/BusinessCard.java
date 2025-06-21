package com.example.cardcase.card.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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


    @OneToMany(mappedBy = "businessCard" , cascade = CascadeType.ALL)
    private List<Relation> relations = new ArrayList<>();
}
