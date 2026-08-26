package com.github.rhafaelcosta.commerce.order.core.domain.model;

import java.util.List;

public interface DomainEventSource {

    List<Object> domainEvents();

    void clearDomainEvents();

}
