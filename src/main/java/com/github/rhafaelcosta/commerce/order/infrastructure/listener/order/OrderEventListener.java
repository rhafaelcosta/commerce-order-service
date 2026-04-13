package com.github.rhafaelcosta.commerce.order.infrastructure.listener.order;

import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderCanceledEvent;
import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderPaidEvent;
import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderPlacedEvent;
import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @EventListener
    public void listen(OrderPlacedEvent event) {

    }

    @EventListener
    public void listen(OrderPaidEvent event) {

    }

    @EventListener
    public void listen(OrderReadyEvent event) {

    }

    @EventListener
    public void listen(OrderCanceledEvent event) {

    }

}