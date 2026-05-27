package com.github.rhafaelcosta.commerce.order.application.checkout;

import com.github.rhafaelcosta.commerce.order.application.commons.AddressData;
import com.github.rhafaelcosta.commerce.order.application.order.query.RecipientData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingInput {

    @Valid
    @NotNull
    private RecipientData recipient;

    @Valid
    @NotNull
    private AddressData address;

}
