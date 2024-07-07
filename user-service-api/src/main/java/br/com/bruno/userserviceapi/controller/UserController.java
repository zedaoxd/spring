package br.com.bruno.userserviceapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.exceptions.StandardError;
import models.responses.UserResponse;

@Tag(name = "UserController", description = "Controller responsible for user operations")
@RequestMapping("/users")
public interface UserController {

    @Operation(summary = "Find user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(
            responseCode = "404", 
            description = "User not found", 
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StandardError.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StandardError.class)
            )
        )
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
        @Parameter(description = "User id", required = true, example = "6689c6eb826b0d4683528032")
        @PathVariable(name = "id") final String id
    );

}
