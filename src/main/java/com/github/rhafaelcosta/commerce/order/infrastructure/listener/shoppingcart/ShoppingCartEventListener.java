package com.github.rhafaelcosta.commerce.order.infrastructure.listener.shoppingcart;

import com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart.ShoppingCartCreatedEvent;
import com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart.ShoppingCartEmptiedEvent;
import com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart.ShoppingCartItemAddedEvent;
import com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart.ShoppingCartItemRemovedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartEventListener {

    @EventListener
    public void listen(ShoppingCartCreatedEvent event) {

    }

    @EventListener
    public void listen(ShoppingCartEmptiedEvent event) {

    }

    @EventListener
    public void listen(ShoppingCartItemAddedEvent event) {

    }

    @EventListener
    public void listen(ShoppingCartItemRemovedEvent event) {

    }

}