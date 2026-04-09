package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.commons.Document;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.FullName;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Phone;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Recipient(FullName fullName, Document document, Phone phone) {

    public Recipient {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
    }

}
