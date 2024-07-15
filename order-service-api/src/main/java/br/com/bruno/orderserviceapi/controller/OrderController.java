package br.com.bruno.orderserviceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.CreateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "OrderController", description = "Controller responsible for managing orders")
@RequestMapping("/orders")
public interface OrderController {

    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Order created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
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
    ResponseEntity<Void> createOrder(@Valid @RequestBody final CreateOrderRequest createOrderRequest);
}
