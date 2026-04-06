package com.github.rhafaelcosta.commerce.order.domain.model.service;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.Order;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.PaymentMethod;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.ShoppingCart;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.ShoppingCartItem;
import com.github.rhafaelcosta.commerce.order.domain.model.exception.ShoppingCartCantProceedToCheckoutException;
import com.github.rhafaelcosta.commerce.order.domain.model.utility.DomainService;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Billing;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Shipping;

import java.util.Set;

@DomainService
public class CheckoutService {

    public Order checkout(ShoppingCart shoppingCart,
                          Billing billing,
                          Shipping shipping,
                          PaymentMethod paymentMethod) {
        if (shoppingCart.isEmpty()) {
            throw new ShoppingCartCantProceedToCheckoutException();
        }

        if (shoppingCart.containsUnavailableItems()) {
            throw new ShoppingCartCantProceedToCheckoutException();
        }

        Set<ShoppingCartItem> items = shoppingCart.items();

        Order order = Order.draft(shoppingCart.customerId());
        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);

        for (ShoppingCartItem item : items) {
            order.addItem(
                    new Product(
                            item.productId(),
                            item.name(),
                            item.price(),
                            item.isAvailable()
                    ),
                    item.quantity());
        }

        order.place();
        shoppingCart.empty();

        return order;
    }

}
