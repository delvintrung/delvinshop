package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.UserWalletRequest;
import com.example.delvin.entity.User;
import com.example.delvin.entity.UserWallet;
import com.example.delvin.repository.UserRepository;
import com.example.delvin.repository.UserWalletRepository;
import com.example.delvin.service.UserWalletService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserWalletServiceImpl implements UserWalletService {
    private final UserWalletRepository userWalletRepository;
    private final UserRepository userRepository;

    public UserWalletServiceImpl(UserWalletRepository userWalletRepository, UserRepository userRepository) {
        this.userWalletRepository = userWalletRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserWallet createUserWallet(UserWalletRequest request) {
        UserWallet userWallet = new UserWallet();
        userWallet.setBalance(request.getBalance());
        userWallet.setCurrency(request.getCurrency());
        UserWallet savedWallet = userWalletRepository.save(userWallet);
        return savedWallet;
    }

    @Override
    public UserWallet getUserWalletByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserWallet userWallet = user.getWallet();
        if (userWallet == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_FOUND);
        }
        return userWallet;
    }

    @Override
    public UserWallet updateUserWallet(Long userId, UserWalletRequest request) {
       User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
       UserWallet userWallet = user.getWallet();
       if (userWallet == null) {
           throw new AppException(ErrorCode.USER_WALLET_NOT_FOUND);
       }
            userWallet.setBalance(request.getBalance());
            userWallet.setCurrency(request.getCurrency());
        return userWalletRepository.save(userWallet);
    }

    @Override
    public UserWallet deleteUserWallet(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserWallet userWallet = user.getWallet();
        if (userWallet == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_FOUND);
        }
        user.setWallet(null);
        userRepository.save(user);
        userWalletRepository.delete(userWallet);
        return userWallet;
    }
}
