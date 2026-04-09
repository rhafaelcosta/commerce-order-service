package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.product.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;

import java.util.Objects;

public class OrderFactory {

    private OrderFactory() {
    }

    public static Order filled(CustomerId customerId,
                               Shipping shipping,
                               Billing billing,
                               PaymentMethod paymentMethod,
                               Product product,
                               Quantity productQuantity) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(shipping);
        Objects.requireNonNull(billing);
        Objects.requireNonNull(paymentMethod);
        Objects.requireNonNull(product);
        Objects.requireNonNull(productQuantity);

        Order order = Order.draft(customerId);

        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, productQuantity);

        return order;
    }

}
