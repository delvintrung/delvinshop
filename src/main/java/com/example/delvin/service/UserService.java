package com.example.delvin.service;

import com.example.delvin.dto.response.UserResponse;
import com.example.delvin.entity.User;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
}
