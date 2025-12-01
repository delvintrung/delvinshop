package com.example.delvin.service;

import com.example.delvin.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
}
