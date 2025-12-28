package com.example.delvin.service;

import com.example.delvin.dto.request.UserWalletRequest;
import com.example.delvin.entity.UserWallet;

public interface UserWalletService {
    UserWallet createUserWallet(UserWalletRequest request);
    UserWallet getUserWalletByUserId(Long userId);
    UserWallet updateUserWallet(Long userId, UserWalletRequest request);
    UserWallet deleteUserWallet(Long userId);
}
