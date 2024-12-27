package com.example.oauth_exercise.global.controller;

import com.example.oauth_exercise.global.dto.CreateAccessTokenRequestDTO;
import com.example.oauth_exercise.global.dto.CreateAccessTokenResponseDTO;
import com.example.oauth_exercise.global.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponseDTO>createNewAccessToken(@RequestBody CreateAccessTokenRequestDTO requestDTO){
        String newAccessToken=tokenService.createNewAccessToken(requestDTO.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponseDTO(newAccessToken));
    }
}
