package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.Specification;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.Customer;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.LoyaltyPoints;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerHasEnoughLoyaltyPointsSpecification implements Specification<Customer> {

    private final LoyaltyPoints expectedLoyaltyPoints;

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return customer.loyaltyPoints().compareTo(expectedLoyaltyPoints) >= 0;
    }

}