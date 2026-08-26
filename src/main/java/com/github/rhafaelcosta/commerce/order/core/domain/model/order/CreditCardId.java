package com.github.rhafaelcosta.commerce.order.core.domain.model.order;

import com.github.rhafaelcosta.commerce.order.core.domain.model.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record CreditCardId(UUID id) {

    public CreditCardId {
        Objects.requireNonNull(id);
    }

    public CreditCardId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

}
