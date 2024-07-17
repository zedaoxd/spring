package models.dtos;

import lombok.*;
import models.responses.OrderResponse;
import models.responses.UserResponse;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class OrderCreatedMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private OrderResponse order;
    private UserResponse customer;
    private UserResponse requester;
}
