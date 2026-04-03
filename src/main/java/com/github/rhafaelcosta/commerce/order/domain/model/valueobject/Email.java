package com.github.rhafaelcosta.commerce.order.domain.model.valueobject;

import com.github.rhafaelcosta.commerce.order.domain.model.validator.FieldValidations;

import static com.github.rhafaelcosta.commerce.order.domain.model.exception.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String value) {

    public Email {
        FieldValidations.requiresValidEmail(value, VALIDATION_ERROR_EMAIL_IS_INVALID);
    }

    @Override
    public String toString() {
        return value;
    }

}
