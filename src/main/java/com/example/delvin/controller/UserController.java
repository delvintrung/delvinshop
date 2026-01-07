package com.example.delvin.controller;

import com.example.delvin.dto.response.UserResponse;
import com.example.delvin.entity.User;
import com.example.delvin.service.impl.UserSeviceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserSeviceImpl userSeviceImpl;
    @GetMapping
    public List<UserResponse> getUsers() {
        return userSeviceImpl.getAllUsers();
    }
}
