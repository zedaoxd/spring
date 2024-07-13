package br.com.bruno.userserviceapi.mapper;

import models.requests.UpdateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.bruno.userserviceapi.entity.User;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = IGNORE,
    nullValueCheckStrategy = ALWAYS
)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserResponse fromEntity(final User user);

    @Mapping(target = "withProfiles", ignore = true)
    @Mapping(target = "withPassword", ignore = true)
    @Mapping(target = "withName", ignore = true)
    @Mapping(target = "withId", ignore = true)
    @Mapping(target = "withEmail", ignore = true)
    @Mapping(target = "id", ignore = true)
    User fromRequest(CreateUserRequest request);

    @Mapping(target = "withProfiles", ignore = true)
    @Mapping(target = "withPassword", ignore = true)
    @Mapping(target = "withName", ignore = true)
    @Mapping(target = "withId", ignore = true)
    @Mapping(target = "withEmail", ignore = true)
    @Mapping(target = "id", ignore = true)
    User update(UpdateUserRequest updateUserRequest, @MappingTarget User entity);
}
