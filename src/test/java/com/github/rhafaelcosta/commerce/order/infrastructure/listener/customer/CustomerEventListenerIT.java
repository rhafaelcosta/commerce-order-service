package com.github.rhafaelcosta.commerce.order.infrastructure.listener.customer;

import com.github.rhafaelcosta.commerce.order.application.customer.loyaltypoints.CustomerLoyaltyPointsApplicationService;
import com.github.rhafaelcosta.commerce.order.application.customer.notification.CustomerNotificationApplicationService;
import com.github.rhafaelcosta.commerce.order.application.customer.notification.CustomerNotificationApplicationService.NotifyNewRegistrationInput;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Email;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.FullName;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerRegisteredEvent;
import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderId;
import com.github.rhafaelcosta.commerce.order.domain.model.order.OrderReadyEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.OffsetDateTime;
import java.util.UUID;

@SpringBootTest
class CustomerEventListenerIT {

    @MockitoSpyBean
    private CustomerEventListener customerEventListener;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockitoSpyBean
    private CustomerNotificationApplicationService notificationApplicationService;

    @MockitoBean
    private CustomerLoyaltyPointsApplicationService loyaltyPointsApplicationService;

    @Test
    void shouldListenOrderReadyEvent() {
        applicationEventPublisher.publishEvent(
                new OrderReadyEvent(
                        new OrderId(),
                        new CustomerId(),
                        OffsetDateTime.now()
                )
        );

        Mockito.verify(customerEventListener).listen(Mockito.any(OrderReadyEvent.class));

        Mockito.verify(loyaltyPointsApplicationService).addLoyaltyPoints(
                Mockito.any(UUID.class),
                Mockito.any(String.class)
        );
    }

    @Test
    void shouldListenCustomerRegisteredEvent() {
        applicationEventPublisher.publishEvent(
                new CustomerRegisteredEvent(
                        new CustomerId(),
                        new FullName("John", "Doe"),
                        new Email("john.doe@email.com"),
                        OffsetDateTime.now()
                )
        );

        Mockito.verify(customerEventListener).listen(Mockito.any(CustomerRegisteredEvent.class));

        Mockito.verify(notificationApplicationService)
                .notifyNewRegistration(
                        Mockito.any(NotifyNewRegistrationInput.class)
                );
    }

}