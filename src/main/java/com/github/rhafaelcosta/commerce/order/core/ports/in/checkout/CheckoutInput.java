package com.github.rhafaelcosta.commerce.order.core.ports.in.checkout;

import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.ShippingInput;
import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.BillingData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutInput {
    @NotNull
    private UUID shoppingCartId;

    @NotBlank
    private String paymentMethod;

    @Valid
    @NotNull
    private ShippingInput shipping;

    @Valid
    @NotNull
    private BillingData billing;

    private UUID creditCardId;
}