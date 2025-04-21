package com.nwn.bank.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "compte_bancaire")
public class CompteBancaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_compte", unique = true, nullable = false, length = 27)
    private String numeroCompte;

    @Column(nullable = false, precision = 15, scale = 2)
    private double solde;

    @Column(name = "creation_date", updatable = false)
    private ZonedDateTime dateCreation;

    @Column(name = "modification_date")
    private ZonedDateTime modificationDate;

    private Boolean active;

    @PrePersist
    protected void onCreate() {
        dateCreation = ZonedDateTime.now();
        modificationDate = ZonedDateTime.now();
        if (active == null) active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        modificationDate = ZonedDateTime.now();
    }

}