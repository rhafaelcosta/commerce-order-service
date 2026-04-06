package com.github.rhafaelcosta.commerce.order.infrastructure.fake;

import com.github.rhafaelcosta.commerce.order.domain.model.service.ProductCatalogService;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.ProductName;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.ProductId;

import java.util.Optional;

public class ProductCatalogServiceFakeImpl implements ProductCatalogService {

    @Override
    public Optional<Product> ofId(ProductId productId) {
        Product product = Product.builder().id(productId)
                .inStock(true)
                .name(new ProductName("Notebook"))
                .price(new Money("3000"))
                .build();

        return Optional.of(product);
    }

}
