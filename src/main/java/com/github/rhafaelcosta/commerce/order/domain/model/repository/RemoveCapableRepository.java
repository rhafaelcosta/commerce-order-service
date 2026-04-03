package com.github.rhafaelcosta.commerce.order.domain.model.repository;

import com.github.rhafaelcosta.commerce.order.domain.model.entity.AggregateRoot;

public interface RemoveCapableRepository<T extends AggregateRoot<ID>, ID> extends Repository<T, ID>  {

    void remove(T t);

    void remove(ID id);

}
