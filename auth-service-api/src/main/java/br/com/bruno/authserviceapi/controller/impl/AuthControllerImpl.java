package br.com.bruno.authserviceapi.controller.impl;

import br.com.bruno.authserviceapi.controller.AuthController;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest authenticateRequest) {
        var authenticationResponse = AuthenticateResponse.builder()
                .token("token")
                .type("Bearer")
                .build();
        return ResponseEntity.ok(authenticationResponse);
    }
}
