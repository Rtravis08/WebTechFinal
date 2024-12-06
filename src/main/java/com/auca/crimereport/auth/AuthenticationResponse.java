package com.auca.crimereport.auth;

import com.auca.crimereport.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String username;
    private Long userId;
    private String token;
    private Role role;
}
