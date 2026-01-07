package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.UserWalletRequest;
import com.example.delvin.dto.response.UserWalletResponse;
import com.example.delvin.entity.User;
import com.example.delvin.entity.UserWallet;
import com.example.delvin.mapper.UserWalletMapper;
import com.example.delvin.repository.UserRepository;
import com.example.delvin.repository.UserWalletRepository;
import com.example.delvin.service.UserWalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserWalletServiceImpl implements UserWalletService {
    private final UserWalletRepository userWalletRepository;
    private final UserWalletMapper userWalletMapper;
    private final UserRepository userRepository;

    @Override
    public UserWalletResponse createUserWallet(UserWalletRequest request) {
        UserWallet userWallet = new UserWallet();
        userWallet.setBalance(request.getBalance());
        userWallet.setCurrency(request.getCurrency());
        UserWallet savedWallet = userWalletRepository.save(userWallet);
        return userWalletMapper.toResponse(savedWallet);
    }

    @Override
    public UserWalletResponse getUserWalletByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserWallet userWallet = user.getWallet();
        if (userWallet == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_FOUND);
        }
        return userWalletMapper.toResponse(userWallet);
    }

    @Override
    public UserWalletResponse updateUserWallet(Long userId, UserWalletRequest request) {
       User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
       UserWallet userWallet = user.getWallet();
       if (userWallet == null) {
           throw new AppException(ErrorCode.USER_WALLET_NOT_FOUND);
       }
            userWallet.setBalance(request.getBalance());
            userWallet.setCurrency(request.getCurrency());
            UserWallet userWallet1 = userWalletRepository.save(userWallet);
            return userWalletMapper.toResponse(userWallet1);
    }

    @Override
    public UserWalletResponse deleteUserWallet(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserWallet userWallet = user.getWallet();
        if (userWallet == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_FOUND);
        }
        user.setWallet(null);
        userRepository.save(user);
        userWalletRepository.delete(userWallet);
        return userWalletMapper.toResponse(userWallet);
    }
}
