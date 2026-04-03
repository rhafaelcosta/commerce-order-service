package com.github.rhafaelcosta.commerce.order.domain.model.exception;

import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.ShoppingCartId;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.ShoppingCartItemId;

public class ShoppingCartDoesNotContainItemException extends DomainException {

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ShoppingCartItemId shoppingCartItemId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM, id, shoppingCartItemId));
    }
}
