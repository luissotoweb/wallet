package com.recarga.pay.wallet.controller;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.recarga.pay.wallet.controllers.WalletController;
import com.recarga.pay.wallet.entities.User;
import com.recarga.pay.wallet.entities.Wallet;
import com.recarga.pay.wallet.services.WalletService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class WalletControllerTest {

    @InjectMocks
    private WalletController walletController;

    @Mock
    private WalletService walletService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateWallet() {
        Wallet wallet = new Wallet();
        when(walletService.createWallet(any(User.class))).thenReturn(wallet);

        Wallet result = walletController.createWallet(1L);

        assertNotNull(result);
        verify(walletService, times(1)).createWallet(any(User.class));
    }

    @Test
    public void testDeposit() {
        doNothing().when(walletService).deposit(anyLong(), anyDouble());

        String response = walletController.deposit(1L, 50.0);

        assertEquals("Deposit successful", response);
        verify(walletService, times(1)).deposit(anyLong(), anyDouble());
    }

    @Test
    public void testWithdraw() {
        doNothing().when(walletService).withdraw(anyLong(), anyDouble());

        String response = walletController.withdraw(1L, 50.0);

        assertEquals("Withdrawal successful", response);
        verify(walletService, times(1)).withdraw(anyLong(), anyDouble());
    }

    @Test
    public void testTransfer() {
        doNothing().when(walletService).transfer(anyLong(), anyLong(), anyDouble());

        String response = walletController.transfer(1L, 2L, 50.0);

        assertEquals("Transfer successful", response);
        verify(walletService, times(1)).transfer(anyLong(), anyLong(), anyDouble());
    }

    @Test
    public void testGetBalance() {
        when(walletService.getBalance(1L)).thenReturn(Optional.of(100.0));

        Double balance = walletController.getBalance(1L);

        assertEquals(100.0, balance, 0.001);
        verify(walletService, times(1)).getBalance(1L);
    }
}
