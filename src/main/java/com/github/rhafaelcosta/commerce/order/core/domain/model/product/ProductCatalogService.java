package com.github.rhafaelcosta.commerce.order.core.domain.model.product;

import java.util.Optional;

public interface ProductCatalogService {

    Optional<Product> ofId(ProductId productId);

}
