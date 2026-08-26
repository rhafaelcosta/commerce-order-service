package com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart;

import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerId;

import java.time.OffsetDateTime;

public record ShoppingCartEmptiedEvent(ShoppingCartId shoppingCartId,
                                       CustomerId customerId,
                                       OffsetDateTime emptiedAt) {
}
