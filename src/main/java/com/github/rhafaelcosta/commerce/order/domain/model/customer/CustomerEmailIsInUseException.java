package com.github.rhafaelcosta.commerce.order.domain.model.customer;

import com.github.rhafaelcosta.commerce.order.domain.model.DomainException;
import com.github.rhafaelcosta.commerce.order.domain.model.ErrorMessages;

public class CustomerEmailIsInUseException extends DomainException {

    public CustomerEmailIsInUseException(CustomerId customerId) {
        super(ErrorMessages.ERROR_CUSTOMER_EMAIL_IS_IN_USE);
    }

}
