package com.github.rhafaelcosta.commerce.order.domain.model.customer;

import java.time.OffsetDateTime;

public record CustomerArchivedEvent(CustomerId customerId, OffsetDateTime archivedAt) {
}