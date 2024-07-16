package br.com.bruno.orderserviceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "OrderController", description = "Controller responsible for managing orders")
@RequestMapping("/orders")
public interface OrderController {

    @Operation(summary = "Find an order by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
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
    ResponseEntity<OrderResponse> findById(
            @Parameter(description = "Order ID", required = true, example = "43f39365-d172-420d-99fa-54d8f587321b")
            @PathVariable("id")
            final UUID id);

    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Order created",
                    content = @Content(mediaType = "application/json")
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
    @PostMapping
    ResponseEntity<Void> createOrder(@Valid @RequestBody final CreateOrderRequest createOrderRequest);

    @Operation(summary = "Update an existing order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order updated",
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
    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> updateOrder(
            @Parameter(description = "Order ID", required = true, example = "43f39365-d172-420d-99fa-54d8f587321b")
            @PathVariable("id")
            final UUID id,
            @Parameter(description = "Update order request", required = true)
            @Valid
            @RequestBody
            final UpdateOrderRequest updateOrderRequest);
}
