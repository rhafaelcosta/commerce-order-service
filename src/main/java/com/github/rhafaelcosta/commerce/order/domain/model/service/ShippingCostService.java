package com.github.rhafaelcosta.commerce.order.domain.model.service;

import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Money;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.ZipCode;
import lombok.Builder;

import java.time.LocalDate;

public interface ShippingCostService {

    CalculationResult calculate(CalculationRequest calculationRequest);

    @Builder
    record CalculationRequest(ZipCode origin, ZipCode destination) {}

    @Builder
    record CalculationResult(Money cost, LocalDate expectedDate) {}
}
