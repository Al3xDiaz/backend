package com.chaoticteam.backend.auth.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "telephone")
@Getter
@Setter
public class TelephoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;
    private String countryCode;
    private boolean whatsapp;
    private boolean telegram;

    // relations
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profileEntity;
}
