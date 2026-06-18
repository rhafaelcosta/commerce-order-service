package com.github.rhafaelcosta.commerce.order.infrastructure.product.client.http;

import com.github.rhafaelcosta.commerce.order.domain.model.commons.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.product.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductCatalogService;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductId;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductName;
import com.github.rhafaelcosta.commerce.order.presentation.BadGatewayException;
import com.github.rhafaelcosta.commerce.order.presentation.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductCatalogServiceHttpImpl implements ProductCatalogService {

    private final ProductCatalogAPIClient productCatalogAPIClient;

    @Override
    public Optional<Product> ofId(ProductId productId) {
        ProductResponse productResponse;
        try {
            productResponse = productCatalogAPIClient.getById(productId.value());
        } catch (ResourceAccessException e) {
            throw new GatewayTimeoutException("Product Catalog API Timeout", e);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            throw new BadGatewayException("Product Catalog API Bad Gateway", e);
        }

        return Optional.of(
                Product.builder()
                        .id(new ProductId(productResponse.getId()))
                        .name(new ProductName(productResponse.getName()))
                        .inStock(productResponse.getInStock())
                        .price(new Money(productResponse.getSalePrice()))
                        .build()
        );
    }

}