package com.github.rhafaelcosta.commerce.order.core.application.checkout;

import com.github.rhafaelcosta.commerce.order.core.application.order.BillingInputDisassembler;
import com.github.rhafaelcosta.commerce.order.core.application.order.ShippingInputDisassembler;
import com.github.rhafaelcosta.commerce.order.core.domain.model.DomainException;
import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.ZipCode;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.Customer;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerNotFoundException;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.Customers;
import com.github.rhafaelcosta.commerce.order.core.domain.model.order.*;
import com.github.rhafaelcosta.commerce.order.core.domain.model.order.shipping.OriginAddressService;
import com.github.rhafaelcosta.commerce.order.core.domain.model.order.shipping.ShippingCostService;
import com.github.rhafaelcosta.commerce.order.core.domain.model.product.ProductCatalogService;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCart;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCartId;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCartNotFoundException;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCarts;
import com.github.rhafaelcosta.commerce.order.core.ports.in.checkout.CheckoutInput;
import com.github.rhafaelcosta.commerce.order.core.ports.in.checkout.ForBuyingWithShoppingCart;
import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.ShippingInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckoutApplicationService implements ForBuyingWithShoppingCart {

    private final Orders orders;
    private final Customers customers;
    private final ShoppingCarts shoppingCarts;
    private final CheckoutService checkoutService;

    private final BillingInputDisassembler billingInputDisassembler;
    private final ShippingInputDisassembler shippingInputDisassembler;

    private final ShippingCostService shippingCostService;
    private final OriginAddressService originAddressService;
    private final ProductCatalogService productCatalogService;

    @Transactional
    @Override
    public String checkout(CheckoutInput input) {
        Objects.requireNonNull(input);
        PaymentMethod paymentMethod = PaymentMethod.valueOf(input.getPaymentMethod());

        CreditCardId creditCardId = null;
        if (paymentMethod.equals(PaymentMethod.CREDIT_CARD)){
            if (input.getCreditCardId() == null) {
                throw new DomainException("Credit card id is required");
            }
            creditCardId = new CreditCardId(input.getCreditCardId());
        }

        ShoppingCartId shoppingCartId = new ShoppingCartId(input.getShoppingCartId());
        ShoppingCart shoppingCart = shoppingCarts.ofId(shoppingCartId).orElseThrow(ShoppingCartNotFoundException::new);
        Customer customer = customers.ofId(shoppingCart.customerId()).orElseThrow(CustomerNotFoundException::new);

        var shippingCalculationResult = calculateShippingCost(input.getShipping());

        Order order = checkoutService.checkout(
                customer,
                shoppingCart,
                billingInputDisassembler.toDomainModel(input.getBilling()),
                shippingInputDisassembler.toDomainModel(input.getShipping(), shippingCalculationResult),
                paymentMethod,
                creditCardId
        );

        orders.add(order);
        shoppingCarts.add(shoppingCart);

        return order.id().toString();
    }

    private ShippingCostService.CalculationResult calculateShippingCost(ShippingInput shipping) {
        ZipCode origin = originAddressService.originAddress().zipCode();
        ZipCode destination = new ZipCode(shipping.getAddress().getZipCode());
        return shippingCostService.calculate(new ShippingCostService.CalculationRequest(origin, destination));
    }

}