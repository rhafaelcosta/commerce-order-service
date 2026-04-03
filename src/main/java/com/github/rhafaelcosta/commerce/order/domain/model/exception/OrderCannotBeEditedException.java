package com.github.rhafaelcosta.commerce.order.domain.model.exception;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.OrderStatus;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.OrderId;

public class OrderCannotBeEditedException extends DomainException {

    public OrderCannotBeEditedException(OrderId id, OrderStatus status) {
        super(String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED, id, status));
    }

}
