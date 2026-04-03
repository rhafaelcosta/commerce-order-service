package com.github.rhafaelcosta.commerce.order.domain.model.valueobject;

import com.github.rhafaelcosta.commerce.order.domain.model.validator.FieldValidations;

public record ProductName(String value) {

    public ProductName {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
