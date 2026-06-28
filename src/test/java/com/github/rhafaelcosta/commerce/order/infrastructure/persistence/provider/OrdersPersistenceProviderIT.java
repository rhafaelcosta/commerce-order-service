package com.github.rhafaelcosta.commerce.order.infrastructure.persistence.provider;

import com.github.rhafaelcosta.commerce.order.domain.model.order.Order;
import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderStatus;
import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.SpringDataAuditingConfig;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.customer.CustomerPersistenceEntityAssembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.customer.CustomerPersistenceEntityDisassembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.customer.CustomersPersistenceProvider;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.order.OrderPersistenceEntityAssembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.order.OrderPersistenceEntityDisassembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.order.OrderPersistenceEntityRepository;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.order.OrdersPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Import({
        SpringDataAuditingConfig.class,
        OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.flyway.locations=classpath:db/migration,classpath:db/testdata")
class OrdersPersistenceProviderIT {

    private final OrdersPersistenceProvider persistenceProvider;
    private final OrderPersistenceEntityRepository entityRepository;

    @Autowired
    public OrdersPersistenceProviderIT(OrdersPersistenceProvider persistenceProvider,
                                       OrderPersistenceEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
        this.persistenceProvider = persistenceProvider;
    }

    @Test
    void shouldUpdateAndKeepPersistenceEntityState() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        long orderId = order.id().value().toLong();
        persistenceProvider.add(order);

        var persistenceEntity = entityRepository.findById(orderId).orElseThrow();

        Assertions.assertThat(persistenceEntity.getStatus()).isEqualTo(OrderStatus.PLACED.name());

        Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();

        order = persistenceProvider.ofId(order.id()).orElseThrow();
        order.markAsPaid();
        persistenceProvider.add(order);

        persistenceEntity = entityRepository.findById(orderId).orElseThrow();

        Assertions.assertThat(persistenceEntity.getStatus()).isEqualTo(OrderStatus.PAID.name());

        Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldAddFindAndNotFailWhenNoTransaction() {
        Order order = OrderTestDataBuilder.anOrder().build();
        persistenceProvider.add(order);

        Assertions.assertThatNoException().isThrownBy(
                () -> persistenceProvider.ofId(order.id()).orElseThrow()
        );
    }


}