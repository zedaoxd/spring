package br.com.bruno.orderserviceapi.service.impl;

import br.com.bruno.orderserviceapi.entity.Order;
import br.com.bruno.orderserviceapi.mapper.OrderMapper;
import br.com.bruno.orderserviceapi.repository.OrderRepository;
import br.com.bruno.orderserviceapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse findById(final UUID id) {
        return orderMapper.fromEntity(find(id));
    }

    @Override
    public List<OrderResponse> findAll() {
        return orderMapper.fromEntities(orderRepository.findAll());
    }

    @Override
    public Page<OrderResponse> findAll(Integer page, Integer size, String sort, String direction) {
        return orderRepository
                .findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort)))
                .map(orderMapper::fromEntity);
    }

    @Override
    public void save(CreateOrderRequest createOrderRequest) {
        orderRepository.save(orderMapper.fromRequest(createOrderRequest));
    }

    @Override
    public OrderResponse update(final UUID id, final UpdateOrderRequest updateOrderRequest) {
        Order entity = find(id);

        entity = orderMapper.fromRequest(entity, updateOrderRequest);

        return orderMapper.fromEntity(orderRepository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        orderRepository.delete(find(id));
    }

    private Order find(final UUID id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found. Id: " + id + ", Type: " + Order.class.getSimpleName()
                ));
    }
}
