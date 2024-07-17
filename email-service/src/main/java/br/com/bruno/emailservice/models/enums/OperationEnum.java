package br.com.bruno.emailservice.models.enums;

import lombok.Getter;

@Getter
public enum OperationEnum {
    ORDER_CREATED("order-created"),
    ORDER_UPDATED("order-updated"),
    ORDER_DELETED("order-deleted");

    private final String operation;

    OperationEnum(String operation) {
        this.operation = operation;
    }
}
