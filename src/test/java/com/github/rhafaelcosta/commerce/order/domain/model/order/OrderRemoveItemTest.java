package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class OrderRemoveItemTest {

    @Test
    void givenDraftOrder_whenRemoveItem_shouldRecalculate() {
        Order order = Order.draft(new CustomerId());

        order.addItem(
                ProductTestDataBuilder.aProduct().build(),
                new Quantity(2)
        );

        OrderItem orderItem1 = order.items().iterator().next();

        order.addItem(
                ProductTestDataBuilder.aProductAltRamMemory().build(),
                new Quantity(3)
        );

        order.removeItem(orderItem1.id());

        Assertions.assertWith(order,
                i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("600.00")),
                i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

    @Test
    void givenDraftOrder_whenTryToRemoveNoExistingItem_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        ThrowableAssert.ThrowingCallable removeTask = () -> order.removeItem(new OrderItemId());

        Assertions.assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class)
                .isThrownBy(removeTask);

        Assertions.assertWith(order,
                i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("6210.00")),
                i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

    @Test
    void givenPlacedOrder_whenTryToRemoveItem_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        ThrowableAssert.ThrowingCallable removeTask = () -> order.removeItem(new OrderItemId());

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(removeTask);

        Assertions.assertWith(order,
                i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("6210.00")),
                i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

}
