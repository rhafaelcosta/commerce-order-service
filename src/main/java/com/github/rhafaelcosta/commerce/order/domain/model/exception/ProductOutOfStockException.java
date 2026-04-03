package com.github.rhafaelcosta.commerce.order.domain.model.exception;

import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.ProductId;

public class ProductOutOfStockException extends DomainException {

    public ProductOutOfStockException(ProductId id) {
        super(String.format(ErrorMessages.ERROR_PRODUCT_IS_OUT_OF_STOCK, id));
    }

}
