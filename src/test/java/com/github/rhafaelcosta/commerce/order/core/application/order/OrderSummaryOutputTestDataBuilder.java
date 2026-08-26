package com.github.rhafaelcosta.commerce.order.core.application.order;

import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.CustomerMinimalOutput;
import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.OrderSummaryOutput;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.core.domain.model.order.OrderId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderSummaryOutputTestDataBuilder {

    public static OrderSummaryOutput.OrderSummaryOutputBuilder placedOrder() {
        return OrderSummaryOutput.builder()
                .id(new OrderId().toString())
                .customer(CustomerMinimalOutput.builder()
                        .id(new CustomerId().value())
                        .firstName("John")
                        .lastName("Doe")
                        .document("12345")
                        .email("johndoe@email.com")
                        .phone("1191234564")
                        .build())
                .totalItems(2)
                .totalAmount(new BigDecimal("41.98"))
                .placedAt(OffsetDateTime.now())
                .paidAt(null)
                .canceledAt(null)
                .readyAt(null)
                .status("PLACED")
                .paymentMethod("GATEWAY_BALANCE");
    }

}