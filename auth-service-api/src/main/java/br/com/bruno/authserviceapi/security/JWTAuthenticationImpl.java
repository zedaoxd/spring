package br.com.bruno.authserviceapi.security;

import br.com.bruno.authserviceapi.security.dto.UserDetailsDTO;
import br.com.bruno.authserviceapi.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse authenticate(final AuthenticateRequest authenticateRequest) {
        try {
            final var authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticateRequest.email(), authenticateRequest.password())
            );

            return buildAuthenticationResponse((UserDetailsDTO) authResult.getPrincipal());
        } catch (BadCredentialsException ex) {
            log.error("Error authenticating user: {}, type: {}", authenticateRequest.email(), JWTAuthenticationImpl.class.getSimpleName());
            throw new BadCredentialsException("Email or password invalid");
        }
    }

    protected AuthenticateResponse buildAuthenticationResponse(final UserDetailsDTO dto) {
        var token = jwtUtils.generateToken(dto);
        return AuthenticateResponse.builder()
                .token(token)
                .type("Bearer")
                .build();
    }
}
