package com.github.rhafaelcosta.commerce.order.core.domain.model;

public interface AggregateRoot<ID> extends DomainEventSource {

    ID id();

}
