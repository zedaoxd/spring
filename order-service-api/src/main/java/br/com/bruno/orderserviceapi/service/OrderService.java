package br.com.bruno.orderserviceapi.service;

import br.com.bruno.orderserviceapi.entity.Order;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

import java.util.UUID;

public interface OrderService {
    Order findById(final UUID id);

    void save(CreateOrderRequest createOrderRequest);

    OrderResponse update(final UUID id, final UpdateOrderRequest updateOrderRequest);

}
