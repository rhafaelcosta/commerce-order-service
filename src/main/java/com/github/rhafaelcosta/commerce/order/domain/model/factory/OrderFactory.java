package com.github.rhafaelcosta.commerce.order.domain.model.factory;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.Order;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.PaymentMethod;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Billing;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Shipping;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.CustomerId;

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
