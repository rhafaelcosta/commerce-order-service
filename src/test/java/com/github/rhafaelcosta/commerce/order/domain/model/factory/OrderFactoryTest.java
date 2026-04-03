package com.github.rhafaelcosta.commerce.order.domain.model.factory;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.Order;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.OrderTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.PaymentMethod;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.ProductTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Billing;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Shipping;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderFactoryTest {

    @Test
    void shouldGenerateFilledOrderThatCanBePlaced() {
        Billing billing = OrderTestDataBuilder.aBilling();
        Shipping shipping = OrderTestDataBuilder.aShipping();

        Product product = ProductTestDataBuilder.aProduct().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

        Quantity quantity = new Quantity(1);
        CustomerId customerId = new CustomerId();

        Order order = OrderFactory.filled(
                customerId, shipping, billing, paymentMethod, product, quantity
        );

        Assertions.assertWith(order,
                o-> Assertions.assertThat(o.shipping()).isEqualTo(shipping),
                o-> Assertions.assertThat(o.billing()).isEqualTo(billing),
                o-> Assertions.assertThat(o.paymentMethod()).isEqualTo(paymentMethod),
                o-> Assertions.assertThat(o.items()).isNotEmpty(),
                o-> Assertions.assertThat(o.customerId()).isNotNull(),
                o-> Assertions.assertThat(o.isDraft()).isTrue()
        );

        order.place();
        Assertions.assertThat(order.isPlaced()).isTrue();
    }

}