package com.example.delvin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
public class LoginRequest {
    private String email;
    private String password;
}
