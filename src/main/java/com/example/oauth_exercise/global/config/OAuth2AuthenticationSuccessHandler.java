package com.example.oauth_exercise.global.config;

import com.example.oauth_exercise.global.config.jwt.TokenProvider;
import com.example.oauth_exercise.global.service.RefreshTokenService;
import com.example.oauth_exercise.user.domain.User;
import com.example.oauth_exercise.user.dto.LoginResponseDTO;
import com.example.oauth_exercise.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        String email=oAuth2User.getAttribute("email");

        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("없는 사용자"));

        String accessToken=tokenProvider.generateToken(user, Duration.ofHours(2));
        String refreshToken=tokenProvider.generateToken(user, Duration.ofHours(7));

        refreshTokenService.saveOrUpdateRefreshToken(user.getId(),refreshToken);

        response.setContentType("application/json;charset=UTF-8"); // UTF-8 인코딩 명시
        response.setCharacterEncoding("UTF-8"); // 추가적으로 문자 인코딩 설정
        new ObjectMapper().writeValue(response.getWriter(),
                new LoginResponseDTO(user.getId(), user.getName(),accessToken, "login success"));
    }
}
