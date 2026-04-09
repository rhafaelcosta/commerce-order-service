package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.AggregateRoot;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.product.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Order implements AggregateRoot<OrderId> {

    private OrderId id;
    private Long version;
    private CustomerId customerId;

    private Money totalAmount;
    private Quantity totalItems;

    private OffsetDateTime paidAt;
    private OffsetDateTime readyAt;
    private OffsetDateTime placedAt;
    private OffsetDateTime canceledAt;

    private Billing billing;
    private Shipping shipping;

    private OrderStatus status;
    private PaymentMethod paymentMethod;

    private Set<OrderItem> items;

    @Builder(builderClassName = "ExistingOrderBuilder", builderMethodName = "existing")
    public Order(OrderId id,
                 Long version,
                 CustomerId customerId,
                 Money totalAmount,
                 Quantity totalItems,
                 OffsetDateTime placedAt,
                 OffsetDateTime paidAt,
                 OffsetDateTime canceledAt,
                 OffsetDateTime readyAt,
                 Billing billing,
                 Shipping shipping,
                 OrderStatus status,
                 PaymentMethod paymentMethod,
                 Set<OrderItem> items) {
        this.setId(id);
        this.setVersion(version);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setPlacedAt(placedAt);
        this.setPaidAt(paidAt);
        this.setCanceledAt(canceledAt);
        this.setReadyAt(readyAt);
        this.setBilling(billing);
        this.setShipping(shipping);
        this.setStatus(status);
        this.setPaymentMethod(paymentMethod);
        this.setItems(items);
    }

    public static Order draft(CustomerId customerId) {
        return new Order(
                new OrderId(),
                null,
                customerId,
                Money.ZERO,
                Quantity.ZERO,
                null,
                null,
                null,
                null,
                null,
                null,
                OrderStatus.DRAFT,
                null,
                new HashSet<>()
        );
    }

    public void addItem(Product product, Quantity quantity) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        this.verifyIfChangeable();

        product.checkOutOfStock();

        OrderItem orderItem = OrderItem.brandNew()
                .orderId(this.id)
                .product(product)
                .quantity(quantity)
                .build();

        if (this.items == null) {
            this.items = new HashSet<>();
        }
        this.items.add(orderItem);
        this.recalculateTotals();
    }

    public void removeItem(OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId);
        this.verifyIfChangeable();

        OrderItem orderItem = this.findOrderItem(orderItemId);
        this.items.remove(orderItem);

        this.recalculateTotals();
    }

    public void place() {
        this.verifyIfCanChangeToPlaced();
        this.changeStatus(OrderStatus.PLACED);
        this.setPlacedAt(OffsetDateTime.now());
    }

    public void cancel() {
        this.changeStatus(OrderStatus.CANCELED);
        this.setCanceledAt(OffsetDateTime.now());
    }

    public void markAsPaid() {
        this.changeStatus(OrderStatus.PAID);
        this.setPaidAt(OffsetDateTime.now());
    }

    public void markAsReady() {
        this.changeStatus(OrderStatus.READY);
        this.setReadyAt(OffsetDateTime.now());
    }

    public void changePaymentMethod(PaymentMethod paymentMethod) {
        Objects.requireNonNull(paymentMethod);

        this.verifyIfChangeable();
        this.setPaymentMethod(paymentMethod);
    }

    public void changeBilling(Billing billing) {
        Objects.requireNonNull(billing);

        this.verifyIfChangeable();
        this.setBilling(billing);
    }

    public void changeShipping(Shipping newShipping) {
        Objects.requireNonNull(newShipping);

        this.verifyIfChangeable();

        if (newShipping.expectedDate().isBefore(LocalDate.now())) {
            throw new OrderInvalidShippingDeliveryDateException(this.id());
        }

        this.setShipping(newShipping);
        this.recalculateTotals();
    }

    public void changeItemQuantity(OrderItemId orderItemId, Quantity quantity) {
        Objects.requireNonNull(quantity);
        Objects.requireNonNull(orderItemId);

        this.verifyIfChangeable();

        OrderItem orderItem = this.findOrderItem(orderItemId);
        orderItem.changeQuantity(quantity);

        this.recalculateTotals();
    }

    public boolean isPaid() {
        return OrderStatus.PAID.equals(this.status());
    }

    public boolean isDraft() {
        return OrderStatus.DRAFT.equals(this.status());
    }

    public boolean isReady() {
        return OrderStatus.READY.equals(this.status());
    }

    public boolean isPlaced() {
        return OrderStatus.PLACED.equals(this.status());
    }

    public boolean isCanceled() {
        return OrderStatus.CANCELED.equals(this.status());
    }

    /* GETTERS */

    public OrderId id() {
        return id;
    }

    public Long version() {
        return this.version;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity totalItems() {
        return totalItems;
    }

    public OffsetDateTime paidAt() {
        return paidAt;
    }

    public OffsetDateTime readyAt() {
        return readyAt;
    }

    public OffsetDateTime placedAt() {
        return placedAt;
    }

    public OffsetDateTime canceledAt() {
        return canceledAt;
    }

    public Billing billing() {
        return billing;
    }

    public Shipping shipping() {
        return shipping;
    }

    public OrderStatus status() {
        return status;
    }

    public PaymentMethod paymentMethod() {
        return paymentMethod;
    }

    public Set<OrderItem> items() {
        return Collections.unmodifiableSet(items);
    }

    /* SETTERS */

    private void setId(OrderId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setVersion(Long version) {
        this.version = version;
    }

    private void setCustomerId(CustomerId customerId) {
        Objects.requireNonNull(customerId);
        this.customerId = customerId;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    private void setTotalItems(Quantity totalItems) {
        Objects.requireNonNull(totalItems);
        this.totalItems = totalItems;
    }

    private void setPaidAt(OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    private void setReadyAt(OffsetDateTime readyAt) {
        this.readyAt = readyAt;
    }

    private void setPlacedAt(OffsetDateTime placedAt) {
        this.placedAt = placedAt;
    }

    private void setCanceledAt(OffsetDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    private void setBilling(Billing billing) {
        this.billing = billing;
    }

    private void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    private void setStatus(OrderStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
    }

    private void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    private void setItems(Set<OrderItem> items) {
        Objects.requireNonNull(items);
        this.items = items;
    }

    /* PRIVATE METHODS */

    private OrderItem findOrderItem(OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId);
        return this.items().stream()
                .filter(i -> i.id().equals(orderItemId))
                .findFirst()
                .orElseThrow(() -> new OrderDoesNotContainOrderItemException(this.id(), orderItemId));
    }

    private void recalculateTotals() {
        Integer totalItemsQuantity = this.items.stream()
                .map(i -> i.quantity().value())
                .reduce(0, Integer::sum);

        BigDecimal totalItemsAmount = this.items.stream()
                .map(i -> i.totalAmount().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal newShippingCost = BigDecimal.ZERO;
        if (this.shipping() != null) {
            newShippingCost = this.shipping().cost().value();
        }

        BigDecimal newTotalAmount = totalItemsAmount.add(newShippingCost);

        this.setTotalItems(new Quantity(totalItemsQuantity));
        this.setTotalAmount(new Money(newTotalAmount));
    }

    private void changeStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus);
        if (this.status().canNotChangeTo(newStatus)) {
            throw new OrderStatusCannotBeChangedException(this.id(), this.status(), newStatus);
        }
        this.setStatus(newStatus);
    }

    private void verifyIfCanChangeToPlaced() {
        if (this.shipping() == null) {
            throw OrderCannotBePlacedException.noShippingInfo(this.id());
        }

        if (this.billing() == null) {
            throw OrderCannotBePlacedException.noBillingInfo(this.id());
        }

        if (this.paymentMethod() == null) {
            throw OrderCannotBePlacedException.noPaymentMethod(this.id());
        }

        if (this.items() == null || this.items().isEmpty()) {
            throw OrderCannotBePlacedException.noItems(this.id());
        }
    }

    private void verifyIfChangeable() {
        if (!isDraft()) {
            throw new OrderCannotBeEditedException(this.id(), this.status());
        }
    }

    /* HASHCODE AND EQUALS */

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
