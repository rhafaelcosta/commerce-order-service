package com.github.rhafaelcosta.commerce.order.domain.model.order;

import com.github.rhafaelcosta.commerce.order.domain.model.Repository;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;

import java.time.Year;
import java.util.List;

public interface Orders extends Repository<Order, OrderId> {

    List<Order> placedByCustomerInYear(CustomerId customerId, Year year);

    long salesQuantityByCustomerInYear(CustomerId customerId, Year year);

    Money totalSoldForCustomer(CustomerId customerId);
}
