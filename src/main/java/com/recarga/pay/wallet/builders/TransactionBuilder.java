package com.recarga.pay.wallet.builders;

import com.recarga.pay.wallet.entities.Transaction;
import com.recarga.pay.wallet.entities.Wallet;
import com.recarga.pay.wallet.entities.enums.TransactionStatus;
import com.recarga.pay.wallet.entities.enums.TransactionType;

import java.time.LocalDateTime;

/**
 * Builder for creating Transaction objects.
 */
public class TransactionBuilder {

    private Double amount;
    private LocalDateTime date;
    private Wallet wallet;
    private TransactionType type;
    private TransactionStatus status;

    /**
     * Sets the amount of the transaction.
     * @param amount the amount
     * @return the builder instance
     */
    public TransactionBuilder withAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Sets the date of the transaction.
     * @param date the date
     * @return the builder instance
     */
    public TransactionBuilder withDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the wallet for the transaction.
     * @param wallet the wallet
     * @return the builder instance
     */
    public TransactionBuilder withWallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    /**
     * Sets the type of the transaction.
     * @param type the type
     * @return the builder instance
     */
    public TransactionBuilder withType(TransactionType type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the status of the transaction.
     * @param status the status
     * @return the builder instance
     */
    public TransactionBuilder withStatus(TransactionStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Builds the transaction.
     * @return the transaction object
     */
    public Transaction build() {
        return new Transaction(amount, date != null ? date : LocalDateTime.now(), wallet, type, status);
    }
}
