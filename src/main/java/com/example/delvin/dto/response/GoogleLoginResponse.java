package com.example.delvin.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleLoginResponse {
    private String email;
    private String name;
    private String avatar;
    private String token;
}
