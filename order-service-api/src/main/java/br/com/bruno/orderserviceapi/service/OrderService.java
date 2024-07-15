package br.com.bruno.orderserviceapi.service;

import models.requests.CreateOrderRequest;

public interface OrderService {
    void save(CreateOrderRequest createOrderRequest);
}
