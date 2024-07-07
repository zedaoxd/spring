package models.requests;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

@With
public record CreateUserRequest(
        @Schema(description = "User name", example = "John Doe")
        @NotEmpty(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,

        @Schema(description = "User email", example = "john@doe.com")
        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
        String email,

        @Schema(description = "User password", example = "123456")
        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password,

        @Schema(description = "User profiles", example = "[\"ROLE_ADMIN\", \"ROLE_CUSTOMER\"]")
        Set<ProfileEnum> profiles
) {
}
