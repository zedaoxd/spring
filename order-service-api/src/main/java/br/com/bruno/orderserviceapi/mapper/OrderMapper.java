package br.com.bruno.orderserviceapi.mapper;

import br.com.bruno.orderserviceapi.entity.Order;
import models.enums.OrderStatusEnum;
import models.requests.CreateOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status) {
        return OrderStatusEnum.toEnum(status);
    }
}
