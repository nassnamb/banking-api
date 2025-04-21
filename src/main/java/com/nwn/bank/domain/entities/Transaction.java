package com.nwn.bank.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant", nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;

    @Column(name = "type_operation", nullable = false)
    private String typeOperation; // "CREDIT", "DEBIT", "TRANSFERT"

    @Column(name = "compte_source")
    private String compteSource;

    @Column(name = "compte_destination")
    private String compteDestination;

    @Column(name = "date_operation", nullable = false)
    private LocalDateTime dateOperation;

    @Column(name = "description")
    private String description;

    // Getters et Setters
    // Constructeurs

    public enum TypeOperation {
        TRANSFERT, CREDIT, DEBIT
    }
}