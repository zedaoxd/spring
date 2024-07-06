package br.com.bruno.userserviceapi.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;
import models.enums.ProfileEnum;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@With
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Set<ProfileEnum> profiles;
}
