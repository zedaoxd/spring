package br.com.bruno.bff.controller.impl;

import br.com.bruno.bff.controller.OrderController;
import br.com.bruno.bff.service.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderResponse> findById(UUID id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @Override
    public ResponseEntity<Page<OrderResponse>> findAll(Integer page, Integer size, String sort, String direction) {
        return ResponseEntity.ok(orderService.findAll(page, size, sort, direction));
    }

    @Override
    public ResponseEntity<Void> createOrder(CreateOrderRequest createOrderRequest) {
        orderService.save(createOrderRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> updateOrder(final UUID id, final UpdateOrderRequest updateOrderRequest) {
        return ResponseEntity.ok(orderService.update(id, updateOrderRequest));
    }

    @Override
    public ResponseEntity<Void> deleteOrder(final UUID id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
