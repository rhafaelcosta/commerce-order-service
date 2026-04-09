package com.github.rhafaelcosta.commerce.order.domain.model.product;

import com.github.rhafaelcosta.commerce.order.domain.model.FieldValidations;

public record ProductName(String value) {

    public ProductName {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
