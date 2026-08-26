package com.github.rhafaelcosta.commerce.order.core.ports.in.shoppingcart;

import java.util.UUID;

public interface ForQueryingShoppingCarts {

    ShoppingCartOutput findById(UUID shoppingCartId);

    ShoppingCartOutput findByCustomerId(UUID customerId);

}