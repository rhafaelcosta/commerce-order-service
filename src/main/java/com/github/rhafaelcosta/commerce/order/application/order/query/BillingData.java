package com.github.rhafaelcosta.commerce.order.application.order.query;

import com.github.rhafaelcosta.commerce.order.application.commons.AddressData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingData {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String document;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @Valid
    @NotNull
    private AddressData address;
}
