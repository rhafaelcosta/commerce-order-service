package com.github.rhafaelcosta.commerce.order.infrastructure.persistence.shoppingcart;

import com.github.rhafaelcosta.commerce.order.application.shoppingcart.query.ShoppingCartOutput;
import com.github.rhafaelcosta.commerce.order.application.shoppingcart.query.ShoppingCartQueryService;
import com.github.rhafaelcosta.commerce.order.application.utility.Mapper;
import com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart.ShoppingCartNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartQueryServiceImpl implements ShoppingCartQueryService {

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