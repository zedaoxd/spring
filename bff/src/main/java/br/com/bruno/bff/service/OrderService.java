package br.com.bruno.bff.service;

import br.com.bruno.bff.client.OrderFeignClient;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderFeignClient orderFeignClient;

    public OrderResponse findById(final UUID id) {
        return orderFeignClient.findById(id).getBody();
    }

    public List<OrderResponse> findAll() {
        return orderFeignClient.findAll().getBody();
    }

    public Page<OrderResponse> findAll(Integer page, Integer size, String sort, String direction) {
        return orderFeignClient.findAll(page, size, sort, direction).getBody();
    }

    public void save(CreateOrderRequest createOrderRequest) {
        orderFeignClient.createOrder(createOrderRequest);
    }

    public OrderResponse update(final UUID id, final UpdateOrderRequest updateOrderRequest) {
        return orderFeignClient.updateOrder(id, updateOrderRequest).getBody();
    }

    public void delete(UUID id) {
        orderFeignClient.deleteOrder(id);
    }
}
