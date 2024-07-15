package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(
        @Schema(description = "Requester ID", example = "668acc06ffcb923f51b46ec9")
        @NotBlank(message = "The `requesterId` is required")
        @Size(min = 24, max = 36, message = "The `requesterId` size must be between 24 and 36")
        String requesterId,

        @Schema(description = "Customer ID", example = "668acc06ffcb923f51b46ec9")
        @NotBlank(message = "The `customerId` is required")
        @Size(min = 24, max = 36, message = "The `customerId` size must be between 24 and 36")
        String customerId,

        @Schema(description = "Title", example = "Order #1")
        @NotBlank(message = "The `title` is required")
        @Size(min = 3, max = 50, message = "The `title` size must be between 3 and 50")
        String title,

        @Schema(description = "Description", example = "Order #1 description")
        @NotBlank(message = "The `description` is required")
        @Size(min = 10, max = 3000, message = "The `description` size must be between 10 and 3000")
        String description,

        @Schema(description = "Status", example = "Open")
        @NotBlank(message = "The `status` is required")
        @Size(min = 4, max = 20, message = "The `status` size must be between 4 and 20")
        String status
) {
}
