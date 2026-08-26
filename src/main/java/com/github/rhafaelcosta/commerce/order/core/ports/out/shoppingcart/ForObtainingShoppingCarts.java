package com.github.rhafaelcosta.commerce.order.core.ports.out.shoppingcart;

import com.github.rhafaelcosta.commerce.order.core.ports.in.shoppingcart.ShoppingCartOutput;

import java.util.UUID;

public interface ForObtainingShoppingCarts {

    ShoppingCartOutput findById(UUID shoppingCartId);

    ShoppingCartOutput findByCustomerId(UUID customerId);

}