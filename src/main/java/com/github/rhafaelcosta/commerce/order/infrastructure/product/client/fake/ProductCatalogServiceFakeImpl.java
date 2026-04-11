package com.github.rhafaelcosta.commerce.order.infrastructure.product.client.fake;

import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductCatalogService;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.product.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductName;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
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
