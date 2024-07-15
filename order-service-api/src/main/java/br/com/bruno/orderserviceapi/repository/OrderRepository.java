package br.com.bruno.orderserviceapi.repository;

import br.com.bruno.orderserviceapi.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {

}
