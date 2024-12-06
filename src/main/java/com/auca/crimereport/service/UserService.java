package com.auca.crimereport.service;

import com.auca.crimereport.model.User;
import com.auca.crimereport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import com.auca.crimereport.util.EmailService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        // Send email notification
        String subject = "Welcome to the Crime Reporting App";
        String message = "Dear " + user.getFullName() + ",\n\nThank you for creating an account with us.\n\nBest regards,\nThe Crime Reporting App Team";
        emailService.sendSimpleMessage(user.getEmail(), subject, message);

        return savedUser;
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            user.setFullName(userDetails.getFullName());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setAddress(userDetails.getAddress());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

