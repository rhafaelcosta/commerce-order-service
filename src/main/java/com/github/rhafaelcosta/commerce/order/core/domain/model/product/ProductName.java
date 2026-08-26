package com.github.rhafaelcosta.commerce.order.core.domain.model.product;

import com.github.rhafaelcosta.commerce.order.core.domain.model.FieldValidations;

public record ProductName(String value) {

    public ProductName {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
