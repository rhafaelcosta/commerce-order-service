package com.github.rhafaelcosta.commerce.order.core.domain.model.order;

import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerId;

import java.time.OffsetDateTime;

public record OrderReadyEvent(OrderId orderId, CustomerId customerId, OffsetDateTime readyAt){
}