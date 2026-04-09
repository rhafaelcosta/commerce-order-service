package com.github.rhafaelcosta.commerce.order.infrastructure.shipping.client.fake;

import com.github.rhafaelcosta.commerce.order.domain.model.order.shipping.OriginAddressService;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Address;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.ZipCode;
import org.springframework.stereotype.Component;

@Component
public class OriginAddressServiceFakeImpl implements OriginAddressService {

    @Override
    public Address originAddress() {
        return Address.builder()
                .street("Bourbon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .build();
    }

}
