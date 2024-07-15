package br.com.bruno.orderserviceapi.service.impl;

import br.com.bruno.orderserviceapi.mapper.OrderMapper;
import br.com.bruno.orderserviceapi.repository.OrderRepository;
import br.com.bruno.orderserviceapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void save(CreateOrderRequest createOrderRequest) {
        orderRepository.save(orderMapper.fromRequest(createOrderRequest));
    }
}
