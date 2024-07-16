package br.com.bruno.orderserviceapi.mapper;

import br.com.bruno.orderserviceapi.entity.Order;
import models.enums.OrderStatusEnum;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = ALWAYS,
        nullValuePropertyMappingStrategy = IGNORE
)
public interface OrderMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "closedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    Order fromRequest(CreateOrderRequest createOrderRequest);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", source = "updateOrderRequest.status", qualifiedByName = "mapStatus")
    @Mapping(target = "closedAt", expression = "java(updateOrderRequest.status() != null && updateOrderRequest.status().equals(models.enums.OrderStatusEnum.CLOSED.getDescription()) ? java.time.LocalDateTime.now() : null)")
    Order fromRequest(@MappingTarget Order entity, UpdateOrderRequest updateOrderRequest);

    OrderResponse fromEntity(Order save);

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status) {
        return OrderStatusEnum.toEnum(status);
    }

}
