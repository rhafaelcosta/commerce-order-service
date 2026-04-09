package com.github.rhafaelcosta.commerce.order.domain.model.product;

import com.github.rhafaelcosta.commerce.order.domain.model.DomainException;
import com.github.rhafaelcosta.commerce.order.domain.model.ErrorMessages;

public class ProductOutOfStockException extends DomainException {

    public ProductOutOfStockException(ProductId id) {
        super(String.format(ErrorMessages.ERROR_PRODUCT_IS_OUT_OF_STOCK, id));
    }

}
