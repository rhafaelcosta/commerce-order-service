package com.github.rhafaelcosta.commerce.order.core.domain.model.customer;

import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.Email;
import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.FullName;

import java.time.OffsetDateTime;

public record CustomerRegisteredEvent(CustomerId customerId,
                                      FullName fullName,
                                      Email email,
                                      OffsetDateTime registeredAt) {
}
