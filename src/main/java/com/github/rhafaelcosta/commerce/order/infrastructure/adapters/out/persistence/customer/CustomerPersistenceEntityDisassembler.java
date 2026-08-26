package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.customer;

import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.*;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.BirthDate;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.Customer;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.LoyaltyPoints;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.commons.AddressEmbeddable;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceEntityDisassembler {

    public Customer toDomainEntity(CustomerPersistenceEntity entity) {
        return Customer.existing()
                .id(new CustomerId(entity.getId()))
                .fullName(new FullName(entity.getFirstName(), entity.getLastName()))
                .birthDate(entity.getBirthDate() != null ? new BirthDate(entity.getBirthDate()) : null)
                .email(new Email(entity.getEmail()))
                .phone(new Phone(entity.getPhone()))
                .document(new Document(entity.getDocument()))
                .loyaltyPoints(new LoyaltyPoints(entity.getLoyaltyPoints()))
                .promotionNotificationsAllowed(entity.getPromotionNotificationsAllowed())
                .archived(entity.getArchived())
                .registeredAt(entity.getRegisteredAt())
                .archivedAt(entity.getArchivedAt())
                .address(toAddressValueObject(entity.getAddress()))
                .version(entity.getVersion())
                .build();
    }

    private Address toAddressValueObject(AddressEmbeddable address) {
        return Address.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(new ZipCode(address.getZipCode()))
                .build();
    }
}
