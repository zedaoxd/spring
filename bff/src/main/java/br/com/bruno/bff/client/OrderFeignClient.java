package br.com.bruno.bff.client;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order-service-api", path = "/orders")
public interface OrderFeignClient {

    @GetMapping
    ResponseEntity<List<OrderResponse>> findAll();

    @GetMapping("/page")
    ResponseEntity<Page<OrderResponse>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction);

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<Void> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest);

    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> updateOrder(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOrderRequest updateOrderRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable UUID id);
}
