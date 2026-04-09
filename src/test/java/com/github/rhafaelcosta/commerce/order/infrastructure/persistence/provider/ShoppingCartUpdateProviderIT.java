package com.github.rhafaelcosta.commerce.order.infrastructure.persistence.provider;

import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart.ShoppingCart;
import com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart.ShoppingCartItem;
import com.github.rhafaelcosta.commerce.order.domain.model.product.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductId;
import com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.customer.CustomerPersistenceEntityAssembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityAssembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.SpringDataAuditingConfig;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.customer.CustomerPersistenceEntityDisassembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.customer.CustomersPersistenceProvider;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityDisassembler;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.shoppingcart.ShoppingCartUpdateProvider;
import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.shoppingcart.ShoppingCartsPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Import({
        SpringDataAuditingConfig.class,
        ShoppingCartUpdateProvider.class,
        CustomersPersistenceProvider.class,
        ShoppingCartsPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        ShoppingCartPersistenceEntityAssembler.class,
        ShoppingCartPersistenceEntityDisassembler.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ShoppingCartUpdateProviderIT {

    private final ShoppingCartUpdateProvider shoppingCartUpdateProvider;
    private final CustomersPersistenceProvider customersPersistenceProvider;
    private final ShoppingCartsPersistenceProvider shoppingCartsPersistenceProvider;

    @Autowired
    public ShoppingCartUpdateProviderIT(ShoppingCartUpdateProvider shoppingCartUpdateProvider,
                                        CustomersPersistenceProvider customersPersistenceProvider,
                                        ShoppingCartsPersistenceProvider shoppingCartsPersistenceProvider) {
        this.shoppingCartUpdateProvider = shoppingCartUpdateProvider;
        this.customersPersistenceProvider = customersPersistenceProvider;
        this.shoppingCartsPersistenceProvider = shoppingCartsPersistenceProvider;
    }

    @BeforeEach
    void setup() {
        if (!customersPersistenceProvider.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customersPersistenceProvider.add(
                    CustomerTestDataBuilder.existingCustomer().build()
            );
        }
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldUpdateItemPriceAndTotalAmount() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();

        Product product1 = ProductTestDataBuilder.aProduct().price(new Money("2000")).build();
        Product product2 = ProductTestDataBuilder.aProductAltRamMemory().price(new Money("200")).build();

        shoppingCart.addItem(product1, new Quantity(2));
        shoppingCart.addItem(product2, new Quantity(1));

        shoppingCartsPersistenceProvider.add(shoppingCart);

        ProductId productIdToUpdate = product1.id();
        Money newProduct1Price = new Money("1500");
        Money expectedNewItemTotalPrice = newProduct1Price.multiply(new Quantity(2));
        Money expectedNewCartTotalAmount = expectedNewItemTotalPrice.add(new Money("200"));

        shoppingCartUpdateProvider.adjustPrice(productIdToUpdate, newProduct1Price);

        ShoppingCart updatedShoppingCart = shoppingCartsPersistenceProvider.ofId(shoppingCart.id()).orElseThrow();

        Assertions.assertThat(updatedShoppingCart.totalAmount()).isEqualTo(expectedNewCartTotalAmount);
        Assertions.assertThat(updatedShoppingCart.totalItems()).isEqualTo(new Quantity(3));

        ShoppingCartItem item = updatedShoppingCart.findItem(productIdToUpdate);

        Assertions.assertThat(item.totalAmount()).isEqualTo(expectedNewItemTotalPrice);
        Assertions.assertThat(item.price()).isEqualTo(newProduct1Price);

    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldUpdateItemAvailability() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();

        Product product1 = ProductTestDataBuilder.aProduct()
                .price(new Money("2000"))
                .inStock(true).build();
        Product product2 = ProductTestDataBuilder.aProductAltRamMemory()
                .price(new Money("200"))
                .inStock(true).build();

        shoppingCart.addItem(product1, new Quantity(2));
        shoppingCart.addItem(product2, new Quantity(1));

        shoppingCartsPersistenceProvider.add(shoppingCart);

        var productIdToUpdate = product1.id();
        var productIdNotToUpdate = product2.id();

        shoppingCartUpdateProvider.changeAvailability(productIdToUpdate, false);

        ShoppingCart updatedShoppingCart = shoppingCartsPersistenceProvider.ofId(shoppingCart.id()).orElseThrow();

        ShoppingCartItem item = updatedShoppingCart.findItem(productIdToUpdate);

        Assertions.assertThat(item.isAvailable()).isFalse();

        ShoppingCartItem item2 = updatedShoppingCart.findItem(productIdNotToUpdate);

        Assertions.assertThat(item2.isAvailable()).isTrue();

    }


}