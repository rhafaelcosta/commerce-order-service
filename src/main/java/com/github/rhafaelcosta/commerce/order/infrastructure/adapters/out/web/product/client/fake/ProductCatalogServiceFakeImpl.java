package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.web.product.client.fake;

import com.github.rhafaelcosta.commerce.order.core.domain.model.product.ProductCatalogService;
import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.core.domain.model.product.Product;
import com.github.rhafaelcosta.commerce.order.core.domain.model.product.ProductName;
import com.github.rhafaelcosta.commerce.order.core.domain.model.product.ProductId;

import java.util.Optional;

//@Component
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
