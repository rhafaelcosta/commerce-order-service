package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.listener.customer;


import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerArchivedEvent;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerRegisteredEvent;
import com.github.rhafaelcosta.commerce.order.core.domain.model.order.OrderReadyEvent;
import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.ForAddingLoyaltyPoints;
import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.ForConfirmCustomerRegistration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerEventListener {

    private final ForAddingLoyaltyPoints forAddingLoyaltyPoints;
    private final ForConfirmCustomerRegistration forConfirmCustomerRegistration;

    @EventListener
    public void listen(CustomerRegisteredEvent event) {
        log.info("CustomerRegisteredEvent listen 1");
        forConfirmCustomerRegistration.confirm(event.customerId().value());
    }

    @EventListener
    public void listen(CustomerArchivedEvent event) {
        log.info("CustomerArchivedEvent listen 1");
    }

    @EventListener
    public void listen(OrderReadyEvent event) {
        forAddingLoyaltyPoints.addLoyaltyPoints(
                event.customerId().value(),
                event.orderId().toString()
        );
    }
}