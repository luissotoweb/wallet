package com.recarga.pay.wallet.controllers;

import com.recarga.pay.wallet.entities.User;
import com.recarga.pay.wallet.entities.Wallet;
import com.recarga.pay.wallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing wallets.
 */
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    /**
     * Creates a new wallet for a user.
     * @param userId the user ID
     * @return the created wallet
     */
    @PostMapping("/create")
    public Wallet createWallet(@RequestParam Long userId) {
        User user = new User();
        user.setId(userId);
        return walletService.createWallet(user);
    }

    /**
     * Deposits funds into a wallet.
     * @param walletId the wallet ID
     * @param amount the amount to deposit
     * @return a success message
     */
    @PostMapping("/deposit")
    public String deposit(@RequestParam Long walletId, @RequestParam Double amount) {
        walletService.deposit(walletId, amount);
        return "Deposit successful";
    }

    /**
     * Withdraws funds from a wallet.
     * @param walletId the wallet ID
     * @param amount the amount to withdraw
     * @return a success message
     */
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long walletId, @RequestParam Double amount) {
        walletService.withdraw(walletId, amount);
        return "Withdrawal successful";
    }

    /**
     * Transfers funds from one wallet to another.
     * @param fromWalletId the sender's wallet ID
     * @param toWalletId the receiver's wallet ID
     * @param amount the amount to transfer
     * @return a success message
     */
    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromWalletId, @RequestParam Long toWalletId, @RequestParam Double amount) {
        walletService.transfer(fromWalletId, toWalletId, amount);
        return "Transfer successful";
    }

    /**
     * Retrieves the balance of a wallet.
     * @param walletId the wallet ID
     * @return the balance
     */
    @GetMapping("/{walletId}/balance")
    public Double getBalance(@PathVariable Long walletId) {
        return walletService.getBalance(walletId).orElseThrow(() -> new RuntimeException("Wallet not found"));
    }
}

