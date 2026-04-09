package com.github.rhafaelcosta.commerce.order.domain.model.customer;

import com.github.rhafaelcosta.commerce.order.domain.model.Repository;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Email;

import java.util.Optional;

public interface Customers extends Repository<Customer, CustomerId> {

    Optional<Customer> ofEmail(Email email);

    boolean isEmailUnique(Email email, CustomerId exceptCustomerId);

}
