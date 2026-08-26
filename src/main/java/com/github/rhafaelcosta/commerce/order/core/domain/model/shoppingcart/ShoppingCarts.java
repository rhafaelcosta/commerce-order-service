package com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart;

import com.github.rhafaelcosta.commerce.order.core.domain.model.RemoveCapableRepository;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerId;

import java.util.Optional;

public interface ShoppingCarts extends RemoveCapableRepository<ShoppingCart, ShoppingCartId> {

    Optional<ShoppingCart> ofCustomer(CustomerId customerId);

}
