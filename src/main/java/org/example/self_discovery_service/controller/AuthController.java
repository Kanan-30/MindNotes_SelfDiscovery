package org.example.self_discovery_service.controller;


import org.example.self_discovery_service.entity.User;
import org.example.self_discovery_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestParam String email, @RequestParam String password) {
        return authService.registerUser(email, password);
    }
}
