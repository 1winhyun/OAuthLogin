package com.example.oauth_exercise.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequestDTO {
    private String refreshToken;
}
