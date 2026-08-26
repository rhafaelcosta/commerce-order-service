package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.shoppingcart;

import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCartProductAdjustmentService;
import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.core.domain.model.product.ProductId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShoppingCartUpdateProvider implements ShoppingCartProductAdjustmentService {

    private final ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository;

    @Override
    @Transactional
    public void adjustPrice(ProductId productId, Money updatedPrice) {
        shoppingCartPersistenceEntityRepository.updateItemPrice(productId.value(), updatedPrice.value());
        shoppingCartPersistenceEntityRepository.recalculateTotalsForCartsWithProduct(productId.value());
    }

    @Override
    @Transactional
    public void changeAvailability(ProductId productId, boolean available) {
        shoppingCartPersistenceEntityRepository.updateItemAvailability(productId.value(), available);
    }

}