package com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart;

import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerAlreadyHaveShoppingCartException;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerNotFoundException;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.Customers;
import com.github.rhafaelcosta.commerce.order.domain.model.DomainService;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;
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
