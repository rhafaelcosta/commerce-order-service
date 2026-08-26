package com.github.rhafaelcosta.commerce.order.core.ports.in.orders;

import com.github.rhafaelcosta.commerce.order.core.ports.in.commons.AddressData;
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
