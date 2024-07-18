package br.com.bruno.bff.service;

import br.com.bruno.bff.client.AuthFeignClient;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticateRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticateResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthFeignClient authFeignClient;

    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) throws Exception {
        return authFeignClient.authenticate(authenticateRequest).getBody();
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return authFeignClient.refreshToken(refreshTokenRequest).getBody();
    }
}
