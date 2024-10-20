package com.recarga.pay.wallet.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.recarga.pay.wallet.entities.enums.TransactionStatus;
import com.recarga.pay.wallet.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a financial transaction in the wallet system.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    @JsonManagedReference
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    // Audit fields
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Constructor for creating a transaction.
     *
     * @param amount the amount of the transaction
     * @param date the date of the transaction
     * @param wallet the wallet involved in the transaction
     * @param type the type of transaction (DEPOSIT, WITHDRAWAL, TRANSFER)
     * @param status the status of the transaction (SUCCESSFUL, FAILED, etc.)
     */
    public Transaction(Double amount, LocalDateTime date, Wallet wallet, TransactionType type, TransactionStatus status) {
        this.amount = amount;
        this.date = date;
        this.wallet = wallet;
        this.type = type;
        this.status = status;
    }

    /**
     * Automatically sets the createdAt and updatedAt fields when the transaction is created.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Automatically updates the updatedAt field when the transaction is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
