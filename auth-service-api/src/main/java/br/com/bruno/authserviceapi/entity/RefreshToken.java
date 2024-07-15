package br.com.bruno.authserviceapi.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = "id")
@Document(collection = "refresh_tokens")
@Builder
public class RefreshToken {

    @Id
    private String id;
    private String username;
    private Instant expiresAt;
    private Instant createdAt;
}
