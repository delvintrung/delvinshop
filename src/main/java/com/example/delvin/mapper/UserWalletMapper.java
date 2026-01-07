package com.example.delvin.mapper;

import com.example.delvin.dto.response.UserWalletResponse;
import com.example.delvin.entity.UserWallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserWalletMapper {
    UserWalletResponse toResponse(UserWallet entity);
}
