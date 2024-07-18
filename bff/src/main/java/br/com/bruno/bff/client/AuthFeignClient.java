package br.com.bruno.bff.client;

import jakarta.validation.Valid;
import models.requests.AuthenticateRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticateResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service-api", path = "/auth")
public interface AuthFeignClient {

    @PostMapping("/login")
    ResponseEntity<AuthenticateResponse> authenticate(@Valid @RequestBody AuthenticateRequest authenticateRequest) throws Exception;

    @PostMapping("/refresh-token")
    ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest);
}
