package br.com.bruno.authserviceapi.service.impl;

import br.com.bruno.authserviceapi.entity.RefreshToken;
import br.com.bruno.authserviceapi.repository.RefreshTokenRepository;
import br.com.bruno.authserviceapi.security.dto.UserDetailsDTO;
import br.com.bruno.authserviceapi.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpired;
import models.exceptions.ResourceNotFoundException;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration.refresh-token}")
    private String refreshTokenExpiration;

    private final RefreshTokenRepository repository;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public RefreshToken save(final String username) {
        return repository.save(
                RefreshToken.builder()
                        .id(randomUUID().toString())
                        .createdAt(now())
                        .expiresAt(now().plusMillis(Long.parseLong(refreshTokenExpiration)))
                        .username(username)
                        .build()
        );
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId){
        final var refreshToken = repository.findById(refreshTokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found. Id: " + refreshTokenId));

        verifyTokenIsValid(refreshToken);

        deleteToken(refreshToken);

        return new RefreshTokenResponse(
                jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername()))
        );
    }

    private void verifyTokenIsValid(final RefreshToken refreshToken) {
        if (refreshToken.getExpiresAt().isBefore(now())) {
            throw new RefreshTokenExpired("Refresh token expired. Id: " + refreshToken.getId());
        }
    }

    private void deleteToken(final RefreshToken refreshToken) {
        repository.delete(refreshToken);
    }
}