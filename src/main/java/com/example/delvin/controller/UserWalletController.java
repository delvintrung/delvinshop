package com.example.delvin.controller;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.dto.request.UserWalletRequest;
import com.example.delvin.dto.response.UserWalletResponse;
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
    public ApiResponse<UserWalletResponse> getUserWallet(Long userId) {
        UserWalletResponse userWallet = userWalletService.getUserWalletByUserId(userId);
        return ApiResponse.<UserWalletResponse>builder()
                .result(userWallet)
                .message("Lấy ví người dùng thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<UserWalletResponse> createUserWallet(@RequestBody UserWalletRequest request) {
        UserWalletResponse userWallet = userWalletService.createUserWallet(request);
        return ApiResponse.<UserWalletResponse>builder()
                .result(userWallet)
                .message("Tạo ví người dùng thành công")
                .build();
    }

    @PatchMapping("/{userId}")
    public ApiResponse<UserWalletResponse> updateUserWallet(@PathVariable Long userId, @RequestBody UserWalletRequest request) {
        UserWalletResponse userWallet = userWalletService.updateUserWallet(userId, request);
        return ApiResponse.<UserWalletResponse>builder()
                .result(userWallet)
                .message("Cập nhật ví người dùng thành công")
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<UserWalletResponse> deleteUserWallet(@PathVariable Long userId) {
        UserWalletResponse userWallet = userWalletService.deleteUserWallet(userId);
        return ApiResponse.<UserWalletResponse>builder()
                .result(userWallet)
                .message("Xóa ví người dùng thành công")
                .build();
    }
}
