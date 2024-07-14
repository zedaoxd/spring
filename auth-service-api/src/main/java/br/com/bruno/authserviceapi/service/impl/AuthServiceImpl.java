package br.com.bruno.authserviceapi.service.impl;

import br.com.bruno.authserviceapi.security.JWTAuthenticationImpl;
import br.com.bruno.authserviceapi.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final JwtUtils jwtUtils;
    private final AuthenticationConfiguration authenticationConfiguration;

    @SneakyThrows
    public AuthenticateResponse authenticate(final AuthenticateRequest authenticateRequest) {
        return new JWTAuthenticationImpl(jwtUtils, authenticationConfiguration.getAuthenticationManager())
                .authenticate(authenticateRequest);
    }
}
