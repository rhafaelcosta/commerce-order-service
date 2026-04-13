package com.github.rhafaelcosta.commerce.order.infrastructure.listener.customer;


import com.github.rhafaelcosta.commerce.order.application.customer.loyaltypoints.CustomerLoyaltyPointsApplicationService;
import com.github.rhafaelcosta.commerce.order.application.customer.notification.CustomerNotificationApplicationService;
import com.github.rhafaelcosta.commerce.order.application.customer.notification.CustomerNotificationApplicationService.NotifyNewRegistrationInput;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerArchivedEvent;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerRegisteredEvent;
import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderReadyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerEventListener {

    private final CustomerNotificationApplicationService customerNotificationApplicationService;
    private final CustomerLoyaltyPointsApplicationService customerLoyaltyPointsApplicationService;

    @EventListener
    public void listen(CustomerRegisteredEvent event) {
        log.info("CustomerRegisteredEvent listen 1");
        NotifyNewRegistrationInput input = new NotifyNewRegistrationInput(
                event.customerId().value(),
                event.fullName().firstName(),
                event.email().value()
        );
        customerNotificationApplicationService.notifyNewRegistration(input);
    }

    @EventListener
    public void listen(CustomerArchivedEvent event) {
        log.info("CustomerArchivedEvent listen 1");
    }

    @EventListener
    public void listen(OrderReadyEvent event) {
        customerLoyaltyPointsApplicationService.addLoyaltyPoints(
                event.customerId().value(),
                event.orderId().toString()
        );
    }
}