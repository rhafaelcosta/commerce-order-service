package com.github.rhafaelcosta.commerce.order.core.application.order;

import com.github.rhafaelcosta.commerce.order.core.ports.in.commons.AddressData;
import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.BillingData;
import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.*;
import com.github.rhafaelcosta.commerce.order.core.domain.model.order.Billing;
import org.springframework.stereotype.Component;

@Component
public class BillingInputDisassembler {

    public Billing toDomainModel(BillingData billingData) {
        AddressData address = billingData.getAddress();
        return Billing.builder()
                .fullName(new FullName(billingData.getFirstName(), billingData.getLastName()))
                .document(new Document(billingData.getDocument()))
                .phone(new Phone(billingData.getPhone()))
                .email(new Email(billingData.getEmail()))
                .address(Address.builder()
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .neighborhood(address.getNeighborhood())
                        .city(address.getCity())
                        .state(address.getState())
                        .zipCode(new ZipCode(address.getZipCode()))
                        .build())
                .build();
    }
}