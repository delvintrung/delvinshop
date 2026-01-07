package com.example.delvin.mapper;

import com.example.delvin.dto.response.UserResponse;
import com.example.delvin.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {UserWalletMapper.class})
public interface UserMapper {
    UserResponse toResponse(User entity);
    List<UserResponse> toResponseList(List<User> entities);
}
