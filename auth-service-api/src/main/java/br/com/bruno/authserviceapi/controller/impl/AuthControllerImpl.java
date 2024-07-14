package br.com.bruno.authserviceapi.controller.impl;

import br.com.bruno.authserviceapi.controller.AuthController;
import br.com.bruno.authserviceapi.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthServiceImpl authService;

    @SneakyThrows
    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(final AuthenticateRequest authenticateRequest) {
        return ResponseEntity.ok(authService.authenticate(authenticateRequest));
    }
}
