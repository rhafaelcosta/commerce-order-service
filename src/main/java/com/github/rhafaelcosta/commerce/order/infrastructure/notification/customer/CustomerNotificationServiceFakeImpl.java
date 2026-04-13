package com.github.rhafaelcosta.commerce.order.infrastructure.notification.customer;

import com.github.rhafaelcosta.commerce.order.application.customer.notification.CustomerNotificationApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerNotificationServiceFakeImpl implements CustomerNotificationApplicationService {

    @Override
    public void notifyNewRegistration(NotifyNewRegistrationInput input) {
        log.info("Welcome {}", input.firstName());
        log.info("User your email to access your account {}", input.email());
    }

}
