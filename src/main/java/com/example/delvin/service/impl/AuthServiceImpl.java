package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.LoginRequest;
import com.example.delvin.dto.request.RegisterRequest;
import com.example.delvin.dto.response.GoogleLoginResponse;
import com.example.delvin.dto.response.LoginResponse;
import com.example.delvin.dto.response.RegisterResponse;
import com.example.delvin.entity.User;
import com.example.delvin.entity.UserWallet;
import com.example.delvin.enums.UserProvider;
import com.example.delvin.repository.UserRepository;
import com.example.delvin.repository.UserWalletRepository;
import com.example.delvin.security.JwtService;
import com.example.delvin.service.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;
    private final UserWalletRepository userWalletRepo;
    private final JwtService jwtService;
    @Value("${google.oauth2.client.id}")
    private String clientId;

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(
            String password,
            String hashed
    ) {
        return BCrypt.checkpw(password, hashed);
    }

    @Override
    public GoogleLoginResponse googleLogin(String idTokenString) {

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new AppException(ErrorCode.GOOGLE_LOGIN_FAIL);
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            String googleId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String picture = (String) payload.get("picture");

            User user = userRepo.findByEmail(email)
                    .orElseGet(() -> {
                        UserWallet wallet = UserWallet.builder()
                                .balance(BigDecimal.ZERO)
                                .currency("VND")
                                .build();
                        User newUser = User.builder()
                                .email(email)
                                .fullName(name)
                                .avatar(picture)
                                .googleId(googleId)
                                .provider(String.valueOf(UserProvider.GOOGLE))
                                .password("")
                                .wallet(wallet)
                                .build();
                        return userRepo.save(newUser);
                    });

            String jwt = jwtService.generateToken(user.getEmail());

            return GoogleLoginResponse.builder()
                    .token(jwt)
                    .email(email)
                    .name(name)
                    .avatar(picture)
                    .build();

        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new AppException(ErrorCode.PASSWORD_MISMATCH);
        }
        boolean existingUser = userRepo.findByEmail(request.getEmail()).isPresent();
        if (existingUser) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String hashPass = hashPassword(request.getPassword());

        UserWallet wallet = UserWallet.builder()
                .balance(BigDecimal.ZERO)
                .currency("VND")
                .build();
        userWalletRepo.save(wallet);

        User newUser = User.builder()
                .fullName(request.getFullName())
                .password(hashPass)
                .email(request.getEmail())
                .wallet(wallet)
                .provider(String.valueOf(UserProvider.MANUAL))
                .build();

        userRepo.save(newUser);
        return RegisterResponse.builder()
                .email(newUser.getEmail())
                .fullName(newUser.getFullName())
                .avatar(newUser.getAvatar())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean passwordMatch = checkPassword(request.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new AppException(ErrorCode.PASSWORD_MISMATCH);
        }
        String jwt = jwtService.generateToken(user.getEmail());
        return LoginResponse.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .token(jwt)
                .build();
    }

    @Override
    public boolean logout(Long userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return true;
    }
}
