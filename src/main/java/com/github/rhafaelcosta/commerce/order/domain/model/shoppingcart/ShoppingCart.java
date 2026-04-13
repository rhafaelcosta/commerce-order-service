package com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart;

import com.github.rhafaelcosta.commerce.order.domain.model.AbstractEventSourceEntity;
import com.github.rhafaelcosta.commerce.order.domain.model.AggregateRoot;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.domain.model.product.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class ShoppingCart extends AbstractEventSourceEntity implements AggregateRoot<ShoppingCartId> {

    private ShoppingCartId id;
    private CustomerId customerId;
    private Money totalAmount;
    private Quantity totalItems;
    private OffsetDateTime createdAt;
    private Set<ShoppingCartItem> items;
    private Long version;

    @Builder(builderClassName = "ExistingShoppingCartBuilder", builderMethodName = "existing")
    public ShoppingCart(ShoppingCartId id,
                        Long version,
                        CustomerId customerId,
                        Money totalAmount,
                        Quantity totalItems,
                        OffsetDateTime createdAt,
                        Set<ShoppingCartItem> items) {
        this.setId(id);
        this.setVersion(version);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setCreatedAt(createdAt);
        this.setItems(items);
    }

    public static ShoppingCart startShopping(CustomerId customerId) {
        ShoppingCart shoppingCart = new ShoppingCart(
                new ShoppingCartId(),
                null,
                customerId,
                Money.ZERO,
                Quantity.ZERO,
                OffsetDateTime.now(),
                new HashSet<>()
        );
        shoppingCart.publishDomainEvent(new ShoppingCartCreatedEvent(
                shoppingCart.id(),
                shoppingCart.customerId(),
                shoppingCart.createdAt()
        ));
        return shoppingCart;
    }

    public void empty() {
        this.items.clear();
        this.setTotalAmount(Money.ZERO);
        this.setTotalItems(Quantity.ZERO);
        this.publishDomainEvent(new ShoppingCartEmptiedEvent(
                this.id(),
                this.customerId(),
                OffsetDateTime.now()
        ));
    }

    public void addItem(Product product, Quantity quantity) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        product.checkOutOfStock();

        ShoppingCartItem shoppingCartItem = ShoppingCartItem.brandNew()
                .shoppingCartId(this.id())
                .productId(product.id())
                .productName(product.name())
                .price(product.price())
                .available(product.inStock())
                .quantity(quantity)
                .build();

        searchItemByProduct(product.id())
                .ifPresentOrElse(i -> updateItem(i, product, quantity), () -> insertItem(shoppingCartItem));

        this.recalculateTotals();

        this.publishDomainEvent(new ShoppingCartItemAddedEvent(
                this.id(),
                this.customerId(),
                product.id(),
                OffsetDateTime.now()
        ));
    }

    public void removeItem(ShoppingCartItemId shoppingCartItemId) {
        ShoppingCartItem item = findItem(shoppingCartItemId);
        this.items.remove(item);
        this.recalculateTotals();
        this.publishDomainEvent(new ShoppingCartItemRemovedEvent(
                this.id(),
                this.customerId(),
                item.productId(),
                OffsetDateTime.now()
        ));
    }

    public void refreshItem(Product product) {
        ShoppingCartItem item = this.findItem(product.id());
        item.refresh(product);
        this.recalculateTotals();
    }

    public void changeItemQuantity(ShoppingCartItemId shoppingCartItemId, Quantity quantity) {
        ShoppingCartItem item = this.findItem(shoppingCartItemId);
        item.changeQuantity(quantity);
        this.recalculateTotals();
    }

    public ShoppingCartItem findItem(ShoppingCartItemId shoppingCartItemId) {
        Objects.requireNonNull(shoppingCartItemId);
        return this.items.stream()
                .filter(item -> item.id().equals(shoppingCartItemId))
                .findFirst()
                .orElseThrow(() -> new ShoppingCartDoesNotContainItemException(this.id(), shoppingCartItemId));
    }

    public ShoppingCartItem findItem(ProductId productId) {
        Objects.requireNonNull(productId);
        return this.items.stream()
                .filter(item -> item.productId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ShoppingCartDoesNotContainProductException(this.id(), productId));

    }

    public boolean containsUnavailableItems() {
        return items.stream().anyMatch(item -> !item.isAvailable());

    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    /* GETTERS */

    public ShoppingCartId id() {
        return id;
    }

    public Long version() {
        return version;
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

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public Set<ShoppingCartItem> items() {
        return items;
    }

    /* SETTERS */

    private void setId(ShoppingCartId id) {
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

    private void setCreatedAt(OffsetDateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
    }

    private void setItems(Set<ShoppingCartItem> items) {
        Objects.requireNonNull(items);
        this.items = items;
    }

    /* PRIVATE METHODS */

    private Optional<ShoppingCartItem> searchItemByProduct(ProductId productId) {
        Objects.requireNonNull(productId);
        return this.items.stream()
                .filter(i -> i.productId().equals(productId))
                .findFirst();
    }

    private void updateItem(ShoppingCartItem shoppingCartItem, Product product, Quantity quantity) {
        shoppingCartItem.refresh(product);
        shoppingCartItem.changeQuantity(shoppingCartItem.quantity().add(quantity));
    }

    private void insertItem(ShoppingCartItem shoppingCartItem) {
        this.items.add(shoppingCartItem);
    }

    private void recalculateTotals() {
        BigDecimal totalItemsAmount = this.items.stream()
                .map(i -> i.totalAmount().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalItemsQuantity = this.items.stream()
                .map(i -> i.quantity().value())
                .reduce(0, Integer::sum);

        this.setTotalAmount(new Money(totalItemsAmount));
        this.setTotalItems(new Quantity(totalItemsQuantity));
    }

    /* HASHCODE and EQUALS*/

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
