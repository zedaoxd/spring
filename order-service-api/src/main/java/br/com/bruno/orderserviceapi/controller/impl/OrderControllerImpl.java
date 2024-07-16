package br.com.bruno.orderserviceapi.controller.impl;

import br.com.bruno.orderserviceapi.controller.OrderController;
import br.com.bruno.orderserviceapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
