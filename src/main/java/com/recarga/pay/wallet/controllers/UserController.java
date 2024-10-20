package com.recarga.pay.wallet.controllers;

import com.recarga.pay.wallet.entities.User;
import com.recarga.pay.wallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new user.
     * @param name the name of the user
     * @return the created user
     */
    @PostMapping
    public User createUser(@RequestParam String name) {
        return userService.createUser(name);
    }

    /**
     * Retrieves a user by ID.
     * @param id the user ID
     * @return the user
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
