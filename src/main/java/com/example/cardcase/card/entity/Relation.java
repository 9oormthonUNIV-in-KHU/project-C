package com.example.cardcase.card.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Relation {

    @Id
    @GeneratedValue
    private Long id;

    private Long given_bc_id;
    private Long user_id;

}
