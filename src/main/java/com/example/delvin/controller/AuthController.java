package com.example.delvin.controller;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.dto.request.GoogleLoginRequest;
import com.example.delvin.dto.response.GoogleLoginResponse;
import com.example.delvin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/google-login")
    public ApiResponse<GoogleLoginResponse> googleLogin(@RequestBody GoogleLoginRequest request) {
        var result = authService.googleLogin(request.getToken());
        return ApiResponse.<GoogleLoginResponse>builder()
                .result(result)
                .message("Đăng nhập Google thành công")
                .build();
    }
}
