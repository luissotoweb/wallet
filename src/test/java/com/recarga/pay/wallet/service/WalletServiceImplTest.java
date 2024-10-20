package com.recarga.pay.wallet.service;

import com.recarga.pay.wallet.entities.User;
import com.recarga.pay.wallet.entities.Wallet;
import com.recarga.pay.wallet.entities.enums.TransactionStatus;
import com.recarga.pay.wallet.entities.enums.TransactionType;
import com.recarga.pay.wallet.repositories.TransactionRepository;
import com.recarga.pay.wallet.repositories.WalletRepository;
import com.recarga.pay.wallet.services.impl.WalletServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class WalletServiceImplTest {

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateWallet() {
        User user = new User(1L, "John Doe", null);
        Wallet wallet = new Wallet(null, 0.0, user, null, null, null);

        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        Wallet result = walletService.createWallet(user);

        assertNotNull(result);
        assertEquals(0.0, result.getCurrentBalance(), 0.001);
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    public void testDeposit() {
        Wallet wallet = new Wallet(1L, 100.0, null, null, null, null);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        walletService.deposit(1L, 50.0);

        assertEquals(150.0, wallet.getCurrentBalance(), 0.001);

        // Validating the builder-generated transaction
        verify(transactionRepository, times(1)).save(argThat(transaction ->
                transaction.getAmount().equals(50.0) &&
                        transaction.getType().equals(TransactionType.DEPOSIT) &&
                        transaction.getStatus().equals(TransactionStatus.SUCCESSFUL)
        ));

        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    public void testWithdraw() {
        Wallet wallet = new Wallet(1L, 200.0, null, null, null, null);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        walletService.withdraw(1L, 50.0);

        assertEquals(150.0, wallet.getCurrentBalance(), 0.001);

        // Validating the builder-generated transaction
        verify(transactionRepository, times(1)).save(argThat(transaction ->
                transaction.getAmount().equals(-50.0) &&
                        transaction.getType().equals(TransactionType.WITHDRAWAL) &&
                        transaction.getStatus().equals(TransactionStatus.SUCCESSFUL)
        ));

        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    public void testTransfer() {
        Wallet fromWallet = new Wallet(1L, 200.0, null, null, null, null);
        Wallet toWallet = new Wallet(2L, 100.0, null, null, null, null);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(fromWallet));
        when(walletRepository.findById(2L)).thenReturn(Optional.of(toWallet));

        walletService.transfer(1L, 2L, 50.0);

        assertEquals(150.0, fromWallet.getCurrentBalance(), 0.001);
        assertEquals(150.0, toWallet.getCurrentBalance(), 0.001);

        // Validate fromWallet transaction
        verify(transactionRepository, times(1)).save(argThat(transaction ->
                transaction.getAmount().equals(-50.0) &&
                        transaction.getWallet().equals(fromWallet) &&
                        transaction.getType().equals(TransactionType.TRANSFER) &&
                        transaction.getStatus().equals(TransactionStatus.SUCCESSFUL)
        ));

        // Validate toWallet transaction
        verify(transactionRepository, times(1)).save(argThat(transaction ->
                transaction.getAmount().equals(50.0) &&
                        transaction.getWallet().equals(toWallet) &&
                        transaction.getType().equals(TransactionType.TRANSFER) &&
                        transaction.getStatus().equals(TransactionStatus.SUCCESSFUL)
        ));

        verify(walletRepository, times(2)).save(any(Wallet.class));
    }
}
