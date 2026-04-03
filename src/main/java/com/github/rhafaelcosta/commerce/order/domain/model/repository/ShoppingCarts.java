package com.github.rhafaelcosta.commerce.order.domain.model.repository;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.ShoppingCart;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.CustomerId;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.ShoppingCartId;

import java.util.Optional;

public interface ShoppingCarts extends RemoveCapableRepository<ShoppingCart, ShoppingCartId> {

    Optional<ShoppingCart> ofCustomer(CustomerId customerId);

}
