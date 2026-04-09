package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.DomainException;
import com.github.rhafaelcosta.commerce.order.domain.model.ErrorMessages;

public class OrderDoesNotContainOrderItemException extends DomainException {

    public OrderDoesNotContainOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAIN_ITEM, id, orderItemId));
    }

}
