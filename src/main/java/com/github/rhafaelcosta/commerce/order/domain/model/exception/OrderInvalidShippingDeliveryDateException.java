package com.github.rhafaelcosta.commerce.order.domain.model.exception;

import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.OrderId;

public class OrderInvalidShippingDeliveryDateException extends DomainException {

    public OrderInvalidShippingDeliveryDateException(OrderId id) {
        super(String.format(ErrorMessages.ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST, id));
    }
}