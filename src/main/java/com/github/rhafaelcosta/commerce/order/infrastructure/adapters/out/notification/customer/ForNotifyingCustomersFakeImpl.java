package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.notification.customer;

import com.github.rhafaelcosta.commerce.order.core.ports.out.customer.ForNotifyingCustomers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ForNotifyingCustomersFakeImpl implements ForNotifyingCustomers {

    @Override
    public void notifyNewRegistration(NotifyNewRegistrationInput input) {
        log.info("Welcome {}", input.firstName());
        log.info("User your email to access your account {}", input.email());
    }

}
