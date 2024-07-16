package br.com.bruno.orderserviceapi.service;

import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

import java.util.UUID;

public interface OrderService {
    OrderResponse findById(final UUID id);

    void save(CreateOrderRequest createOrderRequest);

    OrderResponse update(final UUID id, final UpdateOrderRequest updateOrderRequest);

}
