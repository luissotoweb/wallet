package com.recarga.pay.wallet.services;

import com.recarga.pay.wallet.entities.User;
import com.recarga.pay.wallet.entities.Wallet;

import java.util.Optional;

/**
 * Service interface for handling wallet operations.
 */
public interface WalletService {
    Wallet createWallet(User user);
    void deposit(Long walletId, Double amount);
    void withdraw(Long walletId, Double amount);
    void transfer(Long fromWalletId, Long toWalletId, Double amount);
    Optional<Double> getBalance(Long walletId);
}
