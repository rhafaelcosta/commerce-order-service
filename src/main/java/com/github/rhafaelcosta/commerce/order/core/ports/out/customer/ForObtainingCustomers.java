package com.github.rhafaelcosta.commerce.order.core.ports.out.customer;

import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.CustomerFilter;
import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.CustomerOutput;
import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.CustomerSummaryOutput;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ForObtainingCustomers {

    CustomerOutput findById(UUID customerId);

    Page<CustomerSummaryOutput> filter(CustomerFilter filter);

}