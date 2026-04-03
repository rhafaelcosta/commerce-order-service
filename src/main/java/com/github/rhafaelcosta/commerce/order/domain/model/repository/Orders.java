package com.github.rhafaelcosta.commerce.order.domain.model.repository;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.Order;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.CustomerId;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.OrderId;

import java.time.Year;
import java.util.List;

public interface Orders extends Repository<Order, OrderId> {

    List<Order> placedByCustomerInYear(CustomerId customerId, Year year);

    long salesQuantityByCustomerInYear(CustomerId customerId, Year year);

    Money totalSoldForCustomer(CustomerId customerId);
}
