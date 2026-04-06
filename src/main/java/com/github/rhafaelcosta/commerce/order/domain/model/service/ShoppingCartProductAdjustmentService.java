package com.github.rhafaelcosta.commerce.order.domain.model.service;

import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.ProductId;

public interface ShoppingCartProductAdjustmentService {

    void adjustPrice(ProductId productId, Money updatedPrice);

    void changeAvailability(ProductId productId, boolean available);

}
