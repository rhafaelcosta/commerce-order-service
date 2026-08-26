package com.github.rhafaelcosta.commerce.order.core.domain.model.product;

import com.github.rhafaelcosta.commerce.order.core.domain.model.DomainEntityNotFoundException;
import com.github.rhafaelcosta.commerce.order.core.domain.model.ErrorMessages;

public class ProductNotFoundException extends DomainEntityNotFoundException {

    public ProductNotFoundException() {
    }

    public ProductNotFoundException(ProductId productId) {
        super(String.format(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, productId));
    }

}
