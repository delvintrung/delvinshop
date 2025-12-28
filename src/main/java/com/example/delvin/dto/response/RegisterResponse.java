package com.example.delvin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;

@Data
@AllArgsConstructor
@Setter
@Builder
public class RegisterResponse {
    private String email;
    private String fullName;
    private String avatar;
    private Instant createdAt;
    private Instant updatedAt;
}
