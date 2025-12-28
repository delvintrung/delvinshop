package com.example.delvin.dto.response;

import lombok.*;

@Data
@Setter
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String email;
    private String fullName;
    private String token;
}
