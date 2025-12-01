package com.example.delvin.controller;

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
    UserSeviceImpl userSeviceImpl;
    @GetMapping
    public List<User> getUsers() {
        return userSeviceImpl.getAllUsers();
    }
}
