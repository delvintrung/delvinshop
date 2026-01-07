package com.example.delvin.service;

import com.example.delvin.dto.request.UserWalletRequest;
import com.example.delvin.dto.response.UserWalletResponse;
import com.example.delvin.entity.UserWallet;

public interface UserWalletService {
    UserWalletResponse createUserWallet(UserWalletRequest request);
    UserWalletResponse getUserWalletByUserId(Long userId);
    UserWalletResponse updateUserWallet(Long userId, UserWalletRequest request);
    UserWalletResponse deleteUserWallet(Long userId);
}
