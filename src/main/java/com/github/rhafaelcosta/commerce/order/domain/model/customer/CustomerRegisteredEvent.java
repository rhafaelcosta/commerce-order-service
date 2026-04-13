package com.github.rhafaelcosta.commerce.order.domain.model.customer;

import com.github.rhafaelcosta.commerce.order.domain.model.commons.Email;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.FullName;

import java.time.OffsetDateTime;

public record CustomerRegisteredEvent(CustomerId customerId,
                                      FullName fullName,
                                      Email email,
                                      OffsetDateTime registeredAt) {
}
