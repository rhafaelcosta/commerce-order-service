package com.github.rhafaelcosta.commerce.order.infrastructure.persistence.repository;

import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.AbstractPersistenceIT;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.customer.CustomerPersistenceEntity;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.entity.ShoppingCartPersistenceEntityTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntity;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class ShoppingCartPersistenceEntityRepositoryIT extends AbstractPersistenceIT {

    private final ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository;
    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    private CustomerPersistenceEntity customerPersistenceEntity;

    @Autowired
    ShoppingCartPersistenceEntityRepositoryIT(ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository,
                                                     CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.shoppingCartPersistenceEntityRepository = shoppingCartPersistenceEntityRepository;
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    @BeforeEach
    void setup() {
        UUID customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value();
        if (!customerPersistenceEntityRepository.existsById(customerId)) {
            customerPersistenceEntity = customerPersistenceEntityRepository.saveAndFlush(
                    CustomerPersistenceEntityTestDataBuilder.aCustomer().build()
            );
        }
    }

    @Test
    void shouldPersist() {
        ShoppingCartPersistenceEntity entity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                .customer(customerPersistenceEntity)
                .build();

        shoppingCartPersistenceEntityRepository.saveAndFlush(entity);
        Assertions.assertThat(shoppingCartPersistenceEntityRepository.existsById(entity.getId())).isTrue();

        ShoppingCartPersistenceEntity savedEntity = shoppingCartPersistenceEntityRepository.findById(entity.getId()).orElseThrow();

        Assertions.assertThat(savedEntity.getItems()).isNotEmpty();
    }

    @Test
    void shouldCount() {
        long shoppingCartsCount = shoppingCartPersistenceEntityRepository.count();
        Assertions.assertThat(shoppingCartsCount).isZero();
    }

    @Test
    void shouldSetAuditingValues() {
        ShoppingCartPersistenceEntity entity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                .customer(customerPersistenceEntity)
                .build();
        entity = shoppingCartPersistenceEntityRepository.saveAndFlush(entity);

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();

        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }

}