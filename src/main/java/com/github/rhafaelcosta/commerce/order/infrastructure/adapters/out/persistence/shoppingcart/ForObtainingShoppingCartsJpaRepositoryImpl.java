package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.shoppingcart;

import com.github.rhafaelcosta.commerce.order.core.application.utility.Mapper;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCartNotFoundException;
import com.github.rhafaelcosta.commerce.order.core.ports.in.shoppingcart.ShoppingCartOutput;
import com.github.rhafaelcosta.commerce.order.core.ports.out.shoppingcart.ForObtainingShoppingCarts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
public class ForObtainingShoppingCartsJpaRepositoryImpl implements ForObtainingShoppingCarts {

    private final Mapper mapper;
    private final ShoppingCartPersistenceEntityRepository persistenceRepository;

    @Override
    public ShoppingCartOutput findById(UUID shoppingCartId) {
        return persistenceRepository.findById(shoppingCartId)
                .map(s -> mapper.convert(s, ShoppingCartOutput.class))
                .orElseThrow(ShoppingCartNotFoundException::new);
    }

    @Override
    public ShoppingCartOutput findByCustomerId(UUID customerId) {
        return persistenceRepository.findByCustomer_Id(customerId)
                .map(s -> mapper.convert(s, ShoppingCartOutput.class))
                .orElseThrow(ShoppingCartNotFoundException::new);
    }

}