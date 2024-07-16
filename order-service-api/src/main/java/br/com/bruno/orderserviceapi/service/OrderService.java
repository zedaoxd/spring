package br.com.bruno.orderserviceapi.service;

import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse findById(final UUID id);

    List<OrderResponse> findAll();

    Page<OrderResponse> findAll(Integer page, Integer size, String sort, String direction);

    void save(CreateOrderRequest createOrderRequest);

    OrderResponse update(final UUID id, final UpdateOrderRequest updateOrderRequest);

    void delete(final UUID id);
}
