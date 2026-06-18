package com.github.rhafaelcosta.commerce.order.application.checkout;

import com.github.rhafaelcosta.commerce.order.domain.model.commons.Quantity;
import com.github.rhafaelcosta.commerce.order.domain.model.commons.ZipCode;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.Customer;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerNotFoundException;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.Customers;
import com.github.rhafaelcosta.commerce.order.domain.model.order.*;
import com.github.rhafaelcosta.commerce.order.domain.model.order.shipping.OriginAddressService;
import com.github.rhafaelcosta.commerce.order.domain.model.order.shipping.ShippingCostService;
import com.github.rhafaelcosta.commerce.order.domain.model.product.Product;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductCatalogService;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductId;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BuyNowApplicationService {

    private final BuyNowService buyNowService;
    private final ProductCatalogService productCatalogService;

    private final ShippingCostService shippingCostService;
    private final OriginAddressService originAddressService;

    private final Orders orders;
    private final Customers customers;

    private final ShippingInputDisassembler shippingInputDisassembler;
    private final BillingInputDisassembler billingInputDisassembler;

    @Transactional
    public String buyNow(BuyNowInput input) {
        Objects.requireNonNull(input);
        Quantity quantity = new Quantity(input.getQuantity());
        ProductId productId = new ProductId(input.getProductId());
        CustomerId customerId = new CustomerId(input.getCustomerId());
        PaymentMethod paymentMethod = PaymentMethod.valueOf(input.getPaymentMethod());

        Customer customer = customers.ofId(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Product product = productCatalogService.ofId(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        var shippingCalculationResult = calculateShippingCost(input.getShipping());

        Shipping shipping = shippingInputDisassembler.toDomainModel(input.getShipping(),
                shippingCalculationResult);

        Billing billing = billingInputDisassembler.toDomainModel(input.getBilling());

        Order order = buyNowService.buyNow(
                product, customer, billing, shipping, quantity, paymentMethod
        );

        orders.add(order);

        return order.id().toString();
    }

    private ShippingCostService.CalculationResult calculateShippingCost(ShippingInput shipping) {
        ZipCode origin = originAddressService.originAddress().zipCode();
        ZipCode destination = new ZipCode(shipping.getAddress().getZipCode());
        return shippingCostService.calculate(new ShippingCostService.CalculationRequest(origin, destination));
    }

}
