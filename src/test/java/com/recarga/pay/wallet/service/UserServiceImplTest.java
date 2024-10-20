package com.recarga.pay.wallet.service;

import com.recarga.pay.wallet.entities.User;
import com.recarga.pay.wallet.repositories.UserRepository;
import com.recarga.pay.wallet.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        // Correct constructor for User
        User user = new User(null, "John Doe", null);  // 'wallet' is set to null
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser("John Doe");

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById() {
        User user = new User(1L, "John Doe", null);  // 'wallet' is set to null
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }
}
