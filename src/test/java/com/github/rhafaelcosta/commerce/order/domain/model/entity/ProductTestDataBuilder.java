package com.github.rhafaelcosta.commerce.order.domain.model.entity;

import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.ProductName;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.id.ProductId;

public class ProductTestDataBuilder {

    public static final ProductId DEFAULT_PRODUCT_ID = new ProductId();

    private ProductTestDataBuilder() {
    }

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .name(new ProductName("Notebook X11"))
                .price(new Money("3000"))
                .inStock(true);
    }

    public static Product.ProductBuilder aProductUnavailable() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .name(new ProductName("Desktop FX9000"))
                .price(new Money("5000"))
                .inStock(false);
    }

    public static Product.ProductBuilder aProductAltRamMemory() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .name(new ProductName("4GB RAM"))
                .price(new Money("200"))
                .inStock(true);
    }

    public static Product.ProductBuilder aProductAltMousePad() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .name(new ProductName("Mouse Pad"))
                .price(new Money("100"))
                .inStock(true);
    }

}
