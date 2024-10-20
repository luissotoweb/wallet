package com.recarga.pay.wallet.services.impl;

import com.recarga.pay.wallet.builders.TransactionBuilder;
import com.recarga.pay.wallet.entities.Transaction;
import com.recarga.pay.wallet.entities.User;
import com.recarga.pay.wallet.entities.Wallet;
import com.recarga.pay.wallet.entities.enums.TransactionStatus;
import com.recarga.pay.wallet.entities.enums.TransactionType;
import com.recarga.pay.wallet.repositories.TransactionRepository;
import com.recarga.pay.wallet.repositories.WalletRepository;
import com.recarga.pay.wallet.services.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Implementation of the WalletService for handling wallet operations.
 */
@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Wallet createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setCurrentBalance(0.0);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public void deposit(Long walletId, Double amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setCurrentBalance(wallet.getCurrentBalance() + amount);

        Transaction transaction = new TransactionBuilder()
                .withAmount(amount)
                .withWallet(wallet)
                .withDate(LocalDateTime.now())
                .withType(TransactionType.DEPOSIT)
                .withStatus(TransactionStatus.SUCCESSFUL)
                .build();

        transactionRepository.save(transaction);
        walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public void withdraw(Long walletId, Double amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getCurrentBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        wallet.setCurrentBalance(wallet.getCurrentBalance() - amount);

        Transaction transaction = new TransactionBuilder()
                .withAmount(-amount)
                .withWallet(wallet)
                .withDate(LocalDateTime.now())
                .withType(TransactionType.WITHDRAWAL)
                .withStatus(TransactionStatus.SUCCESSFUL)
                .build();

        transactionRepository.save(transaction);
        walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public void transfer(Long fromWalletId, Long toWalletId, Double amount) {
        Wallet fromWallet = walletRepository.findById(fromWalletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Wallet toWallet = walletRepository.findById(toWalletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (fromWallet.getCurrentBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        fromWallet.setCurrentBalance(fromWallet.getCurrentBalance() - amount);
        toWallet.setCurrentBalance(toWallet.getCurrentBalance() + amount);

        Transaction transactionFrom = new TransactionBuilder()
                .withAmount(-amount)
                .withWallet(fromWallet)
                .withDate(LocalDateTime.now())
                .withType(TransactionType.TRANSFER)
                .withStatus(TransactionStatus.SUCCESSFUL)
                .build();

        Transaction transactionTo = new TransactionBuilder()
                .withAmount(amount)
                .withWallet(toWallet)
                .withDate(LocalDateTime.now())
                .withType(TransactionType.TRANSFER)
                .withStatus(TransactionStatus.SUCCESSFUL)
                .build();

        transactionRepository.save(transactionFrom);
        transactionRepository.save(transactionTo);
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }

    @Override
    public Optional<Double> getBalance(Long walletId) {
        return walletRepository.findById(walletId).map(Wallet::getCurrentBalance);
    }
}

