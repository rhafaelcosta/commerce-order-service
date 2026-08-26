package com.github.rhafaelcosta.commerce.order.core.application.order;

import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.OrderDetailOutput;
import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.OrderSummaryOutput;
import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.ForQueryingOrders;
import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.OrderFilter;
import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.ForObtainingOrders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryService implements ForQueryingOrders {

    private final ForObtainingOrders forObtainingOrders;

    @Override
    public OrderDetailOutput findById(String id) {
        return forObtainingOrders.findById(id);
    }

    @Override
    public Page<OrderSummaryOutput> filter(OrderFilter filter) {
        return forObtainingOrders.filter(filter);
    }

}