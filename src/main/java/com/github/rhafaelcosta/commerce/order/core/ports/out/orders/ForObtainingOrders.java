package com.github.rhafaelcosta.commerce.order.core.ports.out.orders;

import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.OrderFilter;
import org.springframework.data.domain.Page;

public interface ForObtainingOrders {

    OrderDetailOutput findById(String id);

    Page<OrderSummaryOutput> filter(OrderFilter filter);

}
