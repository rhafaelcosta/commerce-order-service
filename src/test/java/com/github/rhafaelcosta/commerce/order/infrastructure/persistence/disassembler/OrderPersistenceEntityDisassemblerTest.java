package com.github.rhafaelcosta.commerce.order.infrastructure.persistence.disassembler;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.Order;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.OrderStatus;
import com.github.rhafaelcosta.commerce.order.domain.model.entity.PaymentMethod;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.CustomerId;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.OrderId;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderPersistenceEntityDisassemblerTest {

    private final OrderPersistenceEntityDisassembler disassembler = new OrderPersistenceEntityDisassembler();

    @Test
    void shouldConvertFromPersistence() {
        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        Order domainEntity = disassembler.toDomainEntity(persistenceEntity);
        assertThat(domainEntity).satisfies(
                s -> assertThat(s.id()).isEqualTo(new OrderId(persistenceEntity.getId())),
                s -> assertThat(s.customerId()).isEqualTo(new CustomerId(persistenceEntity.getCustomerId())),
                s -> assertThat(s.totalAmount()).isEqualTo(new Money(persistenceEntity.getTotalAmount())),
                s -> assertThat(s.totalItems()).isEqualTo(new Quantity(persistenceEntity.getTotalItems())),
                s -> assertThat(s.placedAt()).isEqualTo(persistenceEntity.getPlacedAt()),
                s -> assertThat(s.paidAt()).isEqualTo(persistenceEntity.getPaidAt()),
                s -> assertThat(s.canceledAt()).isEqualTo(persistenceEntity.getCanceledAt()),
                s -> assertThat(s.readyAt()).isEqualTo(persistenceEntity.getReadyAt()),
                s -> assertThat(s.status()).isEqualTo(OrderStatus.valueOf(persistenceEntity.getStatus())),
                s -> assertThat(s.paymentMethod()).isEqualTo(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod()))
        );
    }

}