package com.github.rhafaelcosta.commerce.order.domain.model.exception;

import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.ProductId;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.ShoppingCartItemId;

public class ShoppingCartItemIncompatibleProductException extends DomainException {

    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }

}
