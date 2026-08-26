package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.shoppingcart;

import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.Customer;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCart;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.AbstractPersistenceIT;
import com.github.rhafaelcosta.commerce.order.infrastructure.config.auditing.SpringDataAuditingConfig;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.customer.CustomerPersistenceEntityAssembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.customer.CustomerPersistenceEntityDisassembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.customer.CustomersPersistenceProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@Import({
        SpringDataAuditingConfig.class,
        CustomersPersistenceProvider.class,
        ShoppingCartsPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        ShoppingCartPersistenceEntityAssembler.class,
        ShoppingCartPersistenceEntityDisassembler.class
})
@TestPropertySource(properties = "spring.flyway.locations=classpath:db/migration,classpath:db/testdata")
class ShoppingCartsPersistenceProviderIT extends AbstractPersistenceIT {

    private final CustomersPersistenceProvider customersPersistenceProvider;
    private final ShoppingCartsPersistenceProvider shoppingCartPersistenceProvider;
    private final ShoppingCartPersistenceEntityRepository entityRepository;

    @Autowired
    public ShoppingCartsPersistenceProviderIT(ShoppingCartsPersistenceProvider persistenceProvider,
                                              CustomersPersistenceProvider customersPersistenceProvider,
                                              ShoppingCartPersistenceEntityRepository entityRepository) {
        this.shoppingCartPersistenceProvider = persistenceProvider;
        this.customersPersistenceProvider = customersPersistenceProvider;
        this.entityRepository = entityRepository;
    }

    @Test
    void shouldAddAndFindShoppingCart() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customersPersistenceProvider.add(customer);
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().customerId(customer.id()).build();
        assertThat(shoppingCart.version()).isNull();

        shoppingCartPersistenceProvider.add(shoppingCart);

        assertThat(shoppingCart.version()).isNotNull().isEqualTo(0L);

        ShoppingCart foundCart = shoppingCartPersistenceProvider.ofId(shoppingCart.id()).orElseThrow();
        assertThat(foundCart).isNotNull();
        assertThat(foundCart.id()).isEqualTo(shoppingCart.id());
        assertThat(foundCart.totalItems().value()).isEqualTo(3);
    }

    @Test
    void shouldRemoveShoppingCartById() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customersPersistenceProvider.add(customer);
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().customerId(customer.id()).build();
        shoppingCartPersistenceProvider.add(shoppingCart);
        assertThat(shoppingCartPersistenceProvider.exists(shoppingCart.id())).isTrue();

        shoppingCartPersistenceProvider.remove(shoppingCart.id());

        assertThat(shoppingCartPersistenceProvider.exists(shoppingCart.id())).isFalse();
        assertThat(entityRepository.findById(shoppingCart.id().value())).isEmpty();
    }

    @Test
    void shouldRemoveShoppingCartByEntity() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customersPersistenceProvider.add(customer);
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().customerId(customer.id()).build();
        shoppingCartPersistenceProvider.add(shoppingCart);
        assertThat(shoppingCartPersistenceProvider.exists(shoppingCart.id())).isTrue();

        shoppingCartPersistenceProvider.remove(shoppingCart);

        assertThat(shoppingCartPersistenceProvider.exists(shoppingCart.id())).isFalse();
    }

    @Test
    void shouldFindShoppingCartByCustomerId() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customersPersistenceProvider.add(customer);
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().customerId(customer.id()).build();
        shoppingCartPersistenceProvider.add(shoppingCart);

        ShoppingCart foundCart = shoppingCartPersistenceProvider.ofCustomer(customer.id()).orElseThrow();

        assertThat(foundCart).isNotNull();
        assertThat(foundCart.customerId()).isEqualTo(customer.id());
        assertThat(foundCart.id()).isEqualTo(shoppingCart.id());
    }

    @Test
    void shouldCorrectlyCountShoppingCarts() {
        long initialCount = shoppingCartPersistenceProvider.count();

        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customersPersistenceProvider.add(customer);

        ShoppingCart cart1 = ShoppingCartTestDataBuilder.aShoppingCart().customerId(customer.id()).build();
        shoppingCartPersistenceProvider.add(cart1);

        Customer otherCustomer = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customersPersistenceProvider.add(otherCustomer);

        ShoppingCart cart2 = ShoppingCartTestDataBuilder.aShoppingCart().customerId(otherCustomer.id()).build();
        shoppingCartPersistenceProvider.add(cart2);

        long finalCount = shoppingCartPersistenceProvider.count();

        assertThat(finalCount).isEqualTo(initialCount + 2);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldAddAndFindWhenNoTransaction() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customersPersistenceProvider.add(customer);
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().customerId(customer.id()).build();

        shoppingCartPersistenceProvider.add(shoppingCart);

        assertThatNoException().isThrownBy(() -> {
            ShoppingCart foundCart = shoppingCartPersistenceProvider.ofId(shoppingCart.id()).orElseThrow();
            assertThat(foundCart).isNotNull();
        });
    }

}