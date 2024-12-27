package com.example.oauth_exercise.global.service;

import com.example.oauth_exercise.global.config.jwt.TokenProvider;
import com.example.oauth_exercise.user.domain.User;
import com.example.oauth_exercise.user.service.UserService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken){
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("없는 토큰");
        }
        Long userId=refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user=userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
