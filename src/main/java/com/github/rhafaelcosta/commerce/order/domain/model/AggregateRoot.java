package com.github.rhafaelcosta.commerce.order.domain.model;

public interface AggregateRoot<ID> extends DomainEventSource {

    ID id();

}
