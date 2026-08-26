package com.github.rhafaelcosta.commerce.order.core.ports.in.orders;

import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.OrderDetailOutput;
import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.OrderSummaryOutput;
import org.springframework.data.domain.Page;

public interface ForQueryingOrders {

    OrderDetailOutput findById(String id);

    Page<OrderSummaryOutput> filter(OrderFilter filter);

}
