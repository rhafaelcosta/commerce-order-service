package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.commons.*;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Billing(FullName fullName, Document document, Phone phone, Email email, Address address) {

    public Billing {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(email);
        Objects.requireNonNull(address);
    }

}
