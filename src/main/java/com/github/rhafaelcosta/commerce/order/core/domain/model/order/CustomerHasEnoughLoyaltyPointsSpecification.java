package com.github.rhafaelcosta.commerce.order.core.domain.model.order;

import com.github.rhafaelcosta.commerce.order.core.domain.model.Specification;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.Customer;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.LoyaltyPoints;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerHasEnoughLoyaltyPointsSpecification implements Specification<Customer> {

    private final LoyaltyPoints expectedLoyaltyPoints;

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return customer.loyaltyPoints().compareTo(expectedLoyaltyPoints) >= 0;
    }

}