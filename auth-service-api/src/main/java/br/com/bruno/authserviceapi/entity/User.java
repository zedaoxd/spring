package br.com.bruno.authserviceapi.entity;

import lombok.*;
import models.enums.ProfileEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@AllArgsConstructor
@Document(collection = "users")
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private Set<ProfileEnum> profiles;
}
