package com.github.rhafaelcosta.commerce.order.domain.model.repository;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.Customer;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Email;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.CustomerId;

import java.util.Optional;

public interface Customers extends Repository<Customer, CustomerId> {

    Optional<Customer> ofEmail(Email email);

    boolean isEmailUnique(Email email, CustomerId exceptCustomerId);

}
