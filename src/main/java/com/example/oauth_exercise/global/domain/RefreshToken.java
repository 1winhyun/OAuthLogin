package com.example.oauth_exercise.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id",unique = true)
    private Long userId;

    @Column(name="refresh_token")
    private String refreshToken;

    public RefreshToken(Long userId,String refreshToken){
        this.userId=userId;
        this.refreshToken=refreshToken;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken=newRefreshToken;
        return this;
    }
}
