package br.com.bruno.orderserviceapi.service.impl;

import br.com.bruno.orderserviceapi.clients.UserServiceFeignClient;
import br.com.bruno.orderserviceapi.entity.Order;
import br.com.bruno.orderserviceapi.mapper.OrderMapper;
import br.com.bruno.orderserviceapi.repository.OrderRepository;
import br.com.bruno.orderserviceapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import models.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserServiceFeignClient userServiceFeignClient;

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
        final var customer = validateUserId(createOrderRequest.customerId());
        final var requester = validateUserId(createOrderRequest.requesterId());
        orderRepository.save(orderMapper.fromRequest(createOrderRequest));
    }

    @Override
    public OrderResponse update(final UUID id, final UpdateOrderRequest updateOrderRequest) {
        validateUsers(updateOrderRequest);
        
        Order entity = find(id);

        entity = orderMapper.fromRequest(entity, updateOrderRequest);

        return orderMapper.fromEntity(orderRepository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        orderRepository.delete(find(id));
    }

    private void validateUsers(UpdateOrderRequest updateOrderRequest) {
        if (updateOrderRequest.customerId() != null)
            validateUserId(updateOrderRequest.customerId());

        if (updateOrderRequest.requesterId() != null)
            validateUserId(updateOrderRequest.requesterId());
    }

    private Order find(final UUID id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found. Id: " + id + ", Type: " + Order.class.getSimpleName()
                ));
    }

    private UserResponse validateUserId(final String userId) {
        return userServiceFeignClient.findById(userId).getBody();
    }
}
