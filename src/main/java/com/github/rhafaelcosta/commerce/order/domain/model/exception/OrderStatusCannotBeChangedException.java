package com.github.rhafaelcosta.commerce.order.domain.model.exception;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.OrderStatus;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.OrderId;

public class OrderStatusCannotBeChangedException extends DomainException {

    public OrderStatusCannotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
    }

}
