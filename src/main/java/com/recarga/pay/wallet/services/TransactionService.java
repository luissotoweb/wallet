package com.recarga.pay.wallet.services;

import com.recarga.pay.wallet.entities.Transaction;

import java.util.List;

/**
 * Service interface for handling transaction operations.
 */
public interface TransactionService {
    List<Transaction> getTransactionsByWalletId(Long walletId);
}
