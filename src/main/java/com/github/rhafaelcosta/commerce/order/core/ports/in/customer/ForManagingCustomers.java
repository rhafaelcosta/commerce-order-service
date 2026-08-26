package com.github.rhafaelcosta.commerce.order.core.ports.in.customer;

import java.util.UUID;

public interface ForManagingCustomers {

    UUID create(CustomerInput input);

    void update(UUID id, CustomerUpdateInput input);

    void archive(UUID id);

    void changeEmail(UUID id, String email);
}
