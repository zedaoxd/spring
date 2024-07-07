package br.com.bruno.userserviceapi.mapper;

import org.mapstruct.Mapper;

import br.com.bruno.userserviceapi.entity.User;
import models.responses.UserResponse;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = IGNORE,
    nullValueCheckStrategy = ALWAYS
)
public interface UserMapper {
    UserResponse fromEntity(final User user);
}
