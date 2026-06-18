package com.github.rhafaelcosta.commerce.order.domain.model.customer;

import com.github.rhafaelcosta.commerce.order.domain.model.DomainException;
import com.github.rhafaelcosta.commerce.order.domain.model.ErrorMessages;

public class CustomerAlreadyHaveShoppingCartException extends DomainException {

    public CustomerAlreadyHaveShoppingCartException(CustomerId customerId) {
        super(String.format(ErrorMessages.ERROR_CUSTOMER_ALREADY_HAVE_SHOPPING_CART, customerId));
    }

}
