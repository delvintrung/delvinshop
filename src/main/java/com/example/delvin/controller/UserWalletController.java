package com.example.delvin.controller;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.dto.request.UserWalletRequest;
import com.example.delvin.entity.UserWallet;
import com.example.delvin.service.UserWalletService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-wallets")
public class UserWalletController {
    private final UserWalletService userWalletService;

    public UserWalletController(UserWalletService userWalletService) {
        this.userWalletService = userWalletService;
    }

    @GetMapping
    public ApiResponse<UserWallet> getUserWallet(Long userId) {
        UserWallet userWallet = userWalletService.getUserWalletByUserId(userId);
        return ApiResponse.<UserWallet>builder()
                .result(userWallet)
                .message("Lấy ví người dùng thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<UserWallet> createUserWallet(@RequestBody UserWalletRequest request) {
        UserWallet userWallet = userWalletService.createUserWallet(request);
        return ApiResponse.<UserWallet>builder()
                .result(userWallet)
                .message("Tạo ví người dùng thành công")
                .build();
    }

    @PatchMapping("/{userId}")
    public ApiResponse<UserWallet> updateUserWallet(@PathVariable Long userId, @RequestBody UserWalletRequest request) {
        UserWallet userWallet = userWalletService.updateUserWallet(userId, request);
        return ApiResponse.<UserWallet>builder()
                .result(userWallet)
                .message("Cập nhật ví người dùng thành công")
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<UserWallet> deleteUserWallet(@PathVariable Long userId) {
        UserWallet userWallet = userWalletService.deleteUserWallet(userId);
        return ApiResponse.<UserWallet>builder()
                .result(userWallet)
                .message("Xóa ví người dùng thành công")
                .build();
    }
}
