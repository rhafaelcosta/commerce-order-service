package com.github.rhafaelcosta.commerce.order.domain.model.customer;

import com.github.rhafaelcosta.commerce.order.domain.model.DomainEntityNotFoundException;
import com.github.rhafaelcosta.commerce.order.domain.model.ErrorMessages;

public class CustomerNotFoundException extends DomainEntityNotFoundException {

    public CustomerNotFoundException() {
    }

    public CustomerNotFoundException(CustomerId customerId) {
        super(String.format(ErrorMessages.ERROR_CUSTOMER_NOT_FOUND, customerId));
    }

}
