package com.github.rhafaelcosta.commerce.order.infrastructure.persistence.repository;

import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartPersistenceEntityRepository extends JpaRepository<ShoppingCartPersistenceEntity, UUID> {

    Optional<ShoppingCartPersistenceEntity> findByCustomer_Id(UUID value);

}
