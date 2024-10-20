package com.recarga.pay.wallet.controller;

import com.recarga.pay.wallet.builders.TransactionBuilder;
import com.recarga.pay.wallet.controllers.TransactionController;
import com.recarga.pay.wallet.entities.Transaction;
import com.recarga.pay.wallet.entities.Wallet;
import com.recarga.pay.wallet.entities.enums.TransactionStatus;
import com.recarga.pay.wallet.entities.enums.TransactionType;
import com.recarga.pay.wallet.services.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTransactionsByWallet() {
        Wallet wallet = new Wallet();
        Transaction transaction1 = new TransactionBuilder()
                .withAmount(50.0)
                .withWallet(wallet)
                .withType(TransactionType.DEPOSIT)
                .withStatus(TransactionStatus.SUCCESSFUL)
                .build();

        Transaction transaction2 = new TransactionBuilder()
                .withAmount(30.0)
                .withWallet(wallet)
                .withType(TransactionType.WITHDRAWAL)
                .withStatus(TransactionStatus.SUCCESSFUL)
                .build();

        when(transactionService.getTransactionsByWalletId(1L)).thenReturn(Arrays.asList(transaction1, transaction2));

        List<Transaction> result = transactionController.getTransactionsByWallet(1L);

        assertEquals(2, result.size());
        assertEquals(50.0, result.get(0).getAmount(), 0.001);
        assertEquals(30.0, result.get(1).getAmount(), 0.001);
        verify(transactionService, times(1)).getTransactionsByWalletId(1L);
    }
}
