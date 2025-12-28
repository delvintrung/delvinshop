package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.response.GoogleLoginResponse;
import com.example.delvin.entity.User;
import com.example.delvin.repository.UserRepository;
import com.example.delvin.security.JwtService;
import com.example.delvin.service.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;
    private final JwtService jwtService;
    @Value("${google.oauth2.client.id}")
    private String clientId;

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
                        User newUser = User.builder()
                                .email(email)
                                .fullName(name)
                                .avatar(picture)
                                .googleId(googleId)
                                .provider("GOOGLE")
                                .password("")
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
}
