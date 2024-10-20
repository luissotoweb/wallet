package com.recarga.pay.wallet.services;

import com.recarga.pay.wallet.entities.User;

import java.util.Optional;

public interface UserService {
    User createUser(String name);
    Optional<User> getUserById(Long id);
}
