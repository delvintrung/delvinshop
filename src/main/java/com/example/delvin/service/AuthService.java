package com.example.delvin.service;

import com.example.delvin.dto.request.LoginRequest;
import com.example.delvin.dto.request.RegisterRequest;
import com.example.delvin.dto.response.GoogleLoginResponse;
import com.example.delvin.dto.response.LoginResponse;
import com.example.delvin.dto.response.RegisterResponse;
import com.example.delvin.entity.User;


public interface AuthService {
    GoogleLoginResponse googleLogin(String idToken);
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    boolean logout(Long userId);
}
