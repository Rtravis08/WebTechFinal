package com.auca.crimereport.auth;

import com.auca.crimereport.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Role role;

}

