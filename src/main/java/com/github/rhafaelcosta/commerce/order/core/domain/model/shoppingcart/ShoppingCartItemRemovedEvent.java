package com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart;

import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.core.domain.model.product.ProductId;

import java.time.OffsetDateTime;

public record ShoppingCartItemRemovedEvent(ShoppingCartId shoppingCartId,
                                           CustomerId customerId,
                                           ProductId productId,
                                           OffsetDateTime removedAt) {
}
