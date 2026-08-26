package com.github.rhafaelcosta.commerce.order.core.ports.in.orders;

public interface ForManagingOrders {

    void cancel(String rawOrderId);

    void markAsPaid(String rawOrderId);

    void markAsReady(String rawOrderId);

}
