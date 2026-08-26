package com.github.rhafaelcosta.commerce.order.core.ports.out.orders;

import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.BillingData;
import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.ShippingData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailOutput {
    private String id;
    private CustomerMinimalOutput customer;
    private Integer totalItems;
    private BigDecimal totalAmount;
    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;
    private String status;
    private UUID creditCardId;
    private String paymentMethod;
    private ShippingData shipping;
    private BillingData billing;

    private List<OrderItemDetailOutput> items = new ArrayList<>();
}