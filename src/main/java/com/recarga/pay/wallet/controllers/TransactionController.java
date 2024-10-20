package com.recarga.pay.wallet.controllers;

import com.recarga.pay.wallet.entities.Transaction;
import com.recarga.pay.wallet.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing transactions.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Retrieves all transactions for a specific wallet.
     * @param walletId the wallet ID
     * @return the list of transactions
     */
    @GetMapping("/wallet/{walletId}")
    public List<Transaction> getTransactionsByWallet(@PathVariable Long walletId) {
        return transactionService.getTransactionsByWalletId(walletId);
    }
}
