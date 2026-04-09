package com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart;

import com.github.rhafaelcosta.commerce.order.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductId;

public interface ShoppingCartProductAdjustmentService {

    void adjustPrice(ProductId productId, Money updatedPrice);

    void changeAvailability(ProductId productId, boolean available);

}
