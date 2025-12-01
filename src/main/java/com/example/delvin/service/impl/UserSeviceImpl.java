package com.example.delvin.service.impl;

import com.example.delvin.entity.User;
import com.example.delvin.repository.UserRepository;
import com.example.delvin.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserSeviceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
