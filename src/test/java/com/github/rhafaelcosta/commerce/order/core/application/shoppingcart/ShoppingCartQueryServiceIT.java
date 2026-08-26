package com.github.rhafaelcosta.commerce.order.core.application.shoppingcart;

import com.github.rhafaelcosta.commerce.order.core.application.AbstractApplicationIT;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.Customer;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.Customers;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCart;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCarts;
import com.github.rhafaelcosta.commerce.order.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.github.rhafaelcosta.commerce.order.core.ports.in.shoppingcart.ShoppingCartOutput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ShoppingCartQueryServiceIT extends AbstractApplicationIT {

    @Autowired
    private Customers customers;

    @Autowired
    private ShoppingCarts shoppingCarts;

    @Autowired
    private ForQueryingShoppingCarts queryService;

    @Test
    void shouldFindById() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer);

        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        shoppingCarts.add(shoppingCart);

        ShoppingCartOutput output = queryService.findById(shoppingCart.id().value());
        Assertions.assertWith(output,
                o -> Assertions.assertThat(o.getId()).isEqualTo(shoppingCart.id().value()),
                o -> Assertions.assertThat(o.getCustomerId()).isEqualTo(shoppingCart.customerId().value())
        );
    }

    @Test
    void shouldFindByCustomerId() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        shoppingCarts.add(shoppingCart);

        ShoppingCartOutput output = queryService.findByCustomerId(customer.id().value());
        Assertions.assertWith(output,
                o -> Assertions.assertThat(o.getId()).isEqualTo(shoppingCart.id().value()),
                o -> Assertions.assertThat(o.getCustomerId()).isEqualTo(shoppingCart.customerId().value())
        );
    }

}