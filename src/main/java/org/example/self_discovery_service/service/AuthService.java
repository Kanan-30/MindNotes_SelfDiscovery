package org.example.self_discovery_service.service;

import org.example.self_discovery_service.entity.User;
import org.example.self_discovery_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        String dummyId = UUID.randomUUID().toString();

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(encodedPassword);
        user.setDummyId(dummyId);

        return userRepository.save(user);
    }
}
