package com.github.rhafaelcosta.commerce.order.domain.model.customer;


import com.github.rhafaelcosta.commerce.order.domain.model.DomainException;

import static com.github.rhafaelcosta.commerce.order.domain.model.ErrorMessages.ERROR_CUSTOMER_ARCHIVED;

public class CustomerArchivedException extends DomainException {

    public CustomerArchivedException() {
        super(ERROR_CUSTOMER_ARCHIVED);
    }

    public CustomerArchivedException(Throwable cause) {
        super(ERROR_CUSTOMER_ARCHIVED, cause);
    }

}