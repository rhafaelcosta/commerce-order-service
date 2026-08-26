package com.github.rhafaelcosta.commerce.order.core.domain.model.commons;

import com.github.rhafaelcosta.commerce.order.core.domain.model.FieldValidations;

import static com.github.rhafaelcosta.commerce.order.core.domain.model.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String value) {

    public Email {
        FieldValidations.requiresValidEmail(value, VALIDATION_ERROR_EMAIL_IS_INVALID);
    }

    @Override
    public String toString() {
        return value;
    }

}
