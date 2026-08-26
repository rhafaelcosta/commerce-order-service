package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.listener.customer;

import com.github.rhafaelcosta.commerce.order.core.application.AbstractApplicationIT;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.ForAddingLoyaltyPoints;
import com.github.rhafaelcosta.commerce.order.core.ports.out.customer.ForNotifyingCustomers;
import com.github.rhafaelcosta.commerce.order.core.ports.out.customer.ForNotifyingCustomers.NotifyNewRegistrationInput;
import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.Email;
import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.FullName;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerRegisteredEvent;
import com.github.rhafaelcosta.commerce.order.core.domain.model.order.OrderId;
import com.github.rhafaelcosta.commerce.order.core.domain.model.order.OrderReadyEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@TestPropertySource(properties = "spring.flyway.locations=classpath:db/migration,classpath:db/testdata")
class CustomerEventListenerIT extends AbstractApplicationIT {

    @MockitoSpyBean
    private CustomerEventListener customerEventListener;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockitoBean
    private ForNotifyingCustomers forNotifyingCustomers;

    @MockitoBean
    private ForAddingLoyaltyPoints forAddingLoyaltyPoints;

    @Test
    void shouldListenOrderReadyEvent() {
        applicationEventPublisher.publishEvent(
                new OrderReadyEvent(
                        new OrderId(),
                        new CustomerId(),
                        OffsetDateTime.now()
                )
        );

        verify(customerEventListener).listen(Mockito.any(OrderReadyEvent.class));

        verify(forAddingLoyaltyPoints).addLoyaltyPoints(
                Mockito.any(UUID.class),
                Mockito.any(String.class)
        );
    }

    @Test
    void shouldListenCustomerRegisteredEvent() {
        applicationEventPublisher.publishEvent(
                new CustomerRegisteredEvent(
                        CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID,
                        new FullName("John", "Doe"),
                        new Email("john.doe@email.com"),
                        OffsetDateTime.now()
                )
        );

        verify(customerEventListener)
                .listen(Mockito.any(CustomerRegisteredEvent.class));

        verify(forNotifyingCustomers)
                .notifyNewRegistration(Mockito.any(NotifyNewRegistrationInput.class));
    }

}