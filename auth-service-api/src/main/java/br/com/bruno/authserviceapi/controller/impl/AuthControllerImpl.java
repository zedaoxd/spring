package br.com.bruno.authserviceapi.controller.impl;

import br.com.bruno.authserviceapi.controller.AuthController;
import br.com.bruno.authserviceapi.service.impl.AuthServiceImpl;
import br.com.bruno.authserviceapi.service.impl.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import models.requests.AuthenticateRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticateResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthServiceImpl authService;
    private final RefreshTokenService refreshTokenService;

    @SneakyThrows
    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(final AuthenticateRequest authenticateRequest) {
        return ResponseEntity.ok(authService.authenticate(authenticateRequest));
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok().body(
                refreshTokenService.refreshToken(refreshTokenRequest.refreshToken())
        );
    }
}
