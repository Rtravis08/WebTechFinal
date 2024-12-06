package com.auca.crimereport.auth;

import com.auca.crimereport.config.JwtService;
import com.auca.crimereport.model.User;
import com.auca.crimereport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new Exception("User with username " + request.getUsername() + " already exists.");
        }
        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .username(request.getUsername())
                .role(request.getRole())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .userId(user.getUserId())
                .token(jwtToken)
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new Exception("Authentication failed for user " + request.getUsername());
        }

        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new Exception("User not found: " + request.getUsername()));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .userId(user.getUserId())
                .token(jwtToken)
                .role(user.getRole())
                .build();
    }
}
