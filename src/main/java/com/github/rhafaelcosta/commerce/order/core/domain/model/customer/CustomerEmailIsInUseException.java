package com.github.rhafaelcosta.commerce.order.core.domain.model.customer;

import com.github.rhafaelcosta.commerce.order.core.domain.model.DomainException;
import com.github.rhafaelcosta.commerce.order.core.domain.model.ErrorMessages;

public class CustomerEmailIsInUseException extends DomainException {

    public CustomerEmailIsInUseException(CustomerId customerId) {
        super(ErrorMessages.ERROR_CUSTOMER_EMAIL_IS_IN_USE);
    }

}
