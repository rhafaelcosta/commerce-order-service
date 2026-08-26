package com.github.rhafaelcosta.commerce.order.core.application.customer;

import com.github.rhafaelcosta.commerce.order.core.ports.in.commons.AddressData;
import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.*;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.*;
import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.CustomerInput;
import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.CustomerUpdateInput;
import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.ForManagingCustomers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerManagementApplicationService implements ForManagingCustomers {

    private final Customers customers;
    private final CustomerRegistrationService customerRegistration;

    @Transactional
    @Override
    public UUID create(CustomerInput input) {
        Objects.requireNonNull(input);
        AddressData address = input.getAddress();

        Customer customer = customerRegistration.register(
                new FullName(input.getFirstName(), input.getLastName()),
                new BirthDate(input.getBirthDate()),
                new Email(input.getEmail()),
                new Phone(input.getPhone()),
                new Document(input.getDocument()),
                input.getPromotionNotificationsAllowed(),
                Address.builder()
                        .zipCode(new ZipCode(address.getZipCode()))
                        .state(address.getState())
                        .city(address.getCity())
                        .neighborhood(address.getNeighborhood())
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .build()
        );

        customers.add(customer);

        return customer.id().value();
    }

    @Transactional
    @Override
    public void update(UUID id, CustomerUpdateInput input) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(input);

        Customer customer = customers.ofId(new CustomerId(id)).orElseThrow(CustomerNotFoundException::new);

        customer.changeName(new FullName(input.getFirstName(), input.getLastName()));
        customer.changePhone(new Phone(input.getPhone()));

        if (Boolean.TRUE.equals(input.getPromotionNotificationsAllowed())) {
            customer.enablePromotionNotifications();
        } else {
            customer.disablePromotionNotifications();
        }

        AddressData address = input.getAddress();
        customer.changeAddress(Address.builder()
                .zipCode(new ZipCode(address.getZipCode()))
                .state(address.getState())
                .city(address.getCity())
                .neighborhood(address.getNeighborhood())
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .build());

        customers.add(customer);
    }

    @Transactional
    @Override
    public void archive(UUID id) {
        CustomerId customerId = new CustomerId(id);
        Customer customer = customers.ofId(customerId).orElseThrow(CustomerNotFoundException::new);

        customer.archive();
        customers.add(customer);
    }

    @Transactional
    @Override
    public void changeEmail(UUID id, String email) {
        CustomerId customerId = new CustomerId(id);
        Customer customer = customers.ofId(customerId).orElseThrow(CustomerNotFoundException::new);

        customerRegistration.changeEmail(customer, new  Email(email));
        customers.add(customer);
    }

}
