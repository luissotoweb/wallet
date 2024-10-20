package com.recarga.pay.wallet.controller;

import com.recarga.pay.wallet.controllers.UserController;
import com.recarga.pay.wallet.entities.User;
import com.recarga.pay.wallet.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        // Use the correct constructor for User
        User user = new User(null, "John Doe", null);  // 'wallet' is set to null for now
        when(userService.createUser(anyString())).thenReturn(user);

        User result = userController.createUser("John Doe");

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userService, times(1)).createUser(anyString());
    }

    @Test
    public void testGetUserById() {
        User user = new User(1L, "John Doe", null);  // 'wallet' is set to null for now
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        User result = userController.getUserById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userService, times(1)).getUserById(1L);
    }
}
