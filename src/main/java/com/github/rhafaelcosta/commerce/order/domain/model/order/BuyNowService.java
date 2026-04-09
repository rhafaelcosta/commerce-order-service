package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.DomainService;
import com.github.rhafaelcosta.commerce.order.domain.model.product.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;

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
