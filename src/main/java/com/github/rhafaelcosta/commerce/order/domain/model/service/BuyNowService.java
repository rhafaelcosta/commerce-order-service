package com.github.rhafaelcosta.commerce.order.domain.model.service;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.Order;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.PaymentMethod;
import com.github.rhafaelcosta.commerce.order.domain.model.utility.DomainService;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Billing;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Shipping;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.CustomerId;

@DomainService
public class BuyNowService {

    public Order buyNow(Product product,
                        CustomerId customerId,
                        Billing billing,
                        Shipping shipping,
                        Quantity quantity,
                        PaymentMethod paymentMethod) {

        product.checkOutOfStock();

        Order order = Order.draft(customerId);
        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, quantity);
        order.place();

        return order;
    }

}
