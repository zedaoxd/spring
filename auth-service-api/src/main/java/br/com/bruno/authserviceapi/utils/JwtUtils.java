package br.com.bruno.authserviceapi.utils;

import br.com.bruno.authserviceapi.security.dto.UserDetailsDTO;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.token}")
    private Long expiration;

    public String generateToken(final UserDetailsDTO dto) {
        return Jwts.builder()
                .setSubject(dto.getUsername())
                .claim("id", dto.getId())
                .claim("name", dto.getName())
                .claim("authorities", dto.getAuthorities())
                .signWith(HS256, secret.getBytes(StandardCharsets.UTF_8))
                .setExpiration(new Date(Instant.now().toEpochMilli() + expiration))
                .compact();
    }
}
