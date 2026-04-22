package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.Specification;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.Customer;
import lombok.RequiredArgsConstructor;

import java.time.Year;

@RequiredArgsConstructor
public class CustomerHasOrderedEnoughAtYearSpecification implements Specification<Customer> {

    private final Orders orders;
    private final long expectedOrderCount;

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return orders.salesQuantityByCustomerInYear(
                customer.id(),
                Year.now()
        ) >= expectedOrderCount;
    }

}