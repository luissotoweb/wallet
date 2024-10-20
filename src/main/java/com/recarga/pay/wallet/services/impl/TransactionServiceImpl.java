package com.recarga.pay.wallet.services.impl;

import com.recarga.pay.wallet.entities.Transaction;
import com.recarga.pay.wallet.repositories.TransactionRepository;
import com.recarga.pay.wallet.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the TransactionService for handling transaction operations.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getTransactionsByWalletId(Long walletId) {
        return transactionRepository.findAllByWalletId(walletId);
    }
}
