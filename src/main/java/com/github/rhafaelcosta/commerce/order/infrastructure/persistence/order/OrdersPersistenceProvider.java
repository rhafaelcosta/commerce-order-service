package com.github.rhafaelcosta.commerce.order.infrastructure.persistence.order;

import com.github.rhafaelcosta.commerce.order.domain.model.order.Order;
import com.github.rhafaelcosta.commerce.order.domain.model.order.Orders;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderId;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersPersistenceProvider implements Orders {

    private final EntityManager entityManager;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;
    private final OrderPersistenceEntityRepository persistenceRepository;

    @Override
    public List<Order> placedByCustomerInYear(CustomerId customerId, Year year) {
        var entities = persistenceRepository.placedByCustomerInYear(
                customerId.value(),
                year.getValue()
        );

        return entities.stream().map(disassembler::toDomainEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<Order> ofId(OrderId orderId) {
        Optional<OrderPersistenceEntity> possibleEntity = persistenceRepository.findById(orderId.value().toLong());
        return possibleEntity.map(disassembler::toDomainEntity);
    }

    @Override
    public boolean exists(OrderId orderId) {
        return persistenceRepository.existsById(orderId.value().toLong());
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Order aggregateRoot) {
        long orderId = aggregateRoot.id().value().toLong();

        persistenceRepository.findById(orderId)
                .ifPresentOrElse(
                        (OrderPersistenceEntity persistenceEntity) -> update(aggregateRoot, persistenceEntity),
                        () -> insert(aggregateRoot)
                );
    }

    @Override
    public long count() {
        return persistenceRepository.count();
    }

    @Override
    public long salesQuantityByCustomerInYear(CustomerId customerId, Year year) {
        return this.persistenceRepository.salesQuantityByCustomerInYear(customerId.value(), year.getValue());
    }

    @Override
    public Money totalSoldForCustomer(CustomerId customerId) {
        return new Money(this.persistenceRepository.totalSoldForCustomer(customerId.value()));
    }

    private void insert(Order aggregateRoot) {
        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        persistenceRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    private void update(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        this.entityManager.detach(persistenceEntity);
        persistenceEntity = persistenceRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    @SneakyThrows
    private void updateVersion(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        Field version = aggregateRoot.getClass().getDeclaredField("version");
        version.setAccessible(true);
        ReflectionUtils.setField(version, aggregateRoot, persistenceEntity.getVersion());
        version.setAccessible(false);
    }

}
