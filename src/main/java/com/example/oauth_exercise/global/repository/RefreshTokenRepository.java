package com.example.oauth_exercise.global.repository;

import com.example.oauth_exercise.global.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken>findByUserId(Long userId);
    Optional<RefreshToken>findByRefreshToken(String refreshToken);
}
