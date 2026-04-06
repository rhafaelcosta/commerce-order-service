package com.github.rhafaelcosta.commerce.order.domain.model.service;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.ShoppingCart;
import com.github.rhafaelcosta.commerce.order.domain.model.exception.CustomerAlreadyHaveShoppingCartException;
import com.github.rhafaelcosta.commerce.order.domain.model.exception.CustomerNotFoundException;
import com.github.rhafaelcosta.commerce.order.domain.model.repository.Customers;
import com.github.rhafaelcosta.commerce.order.domain.model.repository.ShoppingCarts;
import com.github.rhafaelcosta.commerce.order.domain.model.utility.DomainService;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.CustomerId;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ShoppingService {

    private final ShoppingCarts shoppingCarts;
    private final Customers customers;

    public ShoppingCart startShopping(CustomerId customerId) {
        if (!customers.exists(customerId)) {
            throw new CustomerNotFoundException();
        }

        if (shoppingCarts.ofCustomer(customerId).isPresent()) {
            throw new CustomerAlreadyHaveShoppingCartException();
        }

        return ShoppingCart.startShopping(customerId);
    }

}
