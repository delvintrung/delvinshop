package com.example.delvin.service.impl;

import com.example.delvin.dto.response.UserResponse;
import com.example.delvin.entity.User;
import com.example.delvin.mapper.UserMapper;
import com.example.delvin.repository.UserRepository;
import com.example.delvin.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserSeviceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse getUserById(Long id) {

        User user =  userRepository.getReferenceById(id);
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {


        List<User> userResponseList = userRepository.findAll();
        return userMapper.toResponseList(userResponseList);
    }

}
