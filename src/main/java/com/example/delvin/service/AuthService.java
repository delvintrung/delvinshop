package com.example.delvin.service;

import com.example.delvin.dto.response.GoogleLoginResponse;


public interface AuthService {
    GoogleLoginResponse googleLogin(String idToken);
}
