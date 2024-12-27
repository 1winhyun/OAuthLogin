package com.example.oauth_exercise.global.config;

import com.example.oauth_exercise.global.config.jwt.JwtAuthenticationFilter;
import com.example.oauth_exercise.global.config.jwt.TokenProvider;
import com.example.oauth_exercise.global.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public WebSecurityCustomizer configure(){
        return (web)->web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        return http
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/oauth2/**"),
                                new AntPathRequestMatcher("/api/token")
                        ).permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2->oauth2
                        .userInfoEndpoint(userInfo->userInfo
                                .userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
