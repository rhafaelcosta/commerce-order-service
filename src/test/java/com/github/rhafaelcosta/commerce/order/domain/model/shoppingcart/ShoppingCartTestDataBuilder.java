package com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart;

import com.github.rhafaelcosta.commerce.order.domain.model.commons.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;

public class ShoppingCartTestDataBuilder {

    public CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;
    public static final ShoppingCartId DEFAULT_SHOPPING_CART_ID = new ShoppingCartId();
    private boolean withItems = true;

    private ShoppingCartTestDataBuilder() {
    }

    public static ShoppingCartTestDataBuilder aShoppingCart() {
        return new ShoppingCartTestDataBuilder();
    }

    public ShoppingCart build() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        if (withItems) {
            cart.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(2));
            cart.addItem(ProductTestDataBuilder.aProductAltRamMemory().build(), new Quantity(1));
        }

        return cart;
    }

    public ShoppingCartTestDataBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public ShoppingCartTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }

}
