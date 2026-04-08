package com.github.rhafaelcosta.commerce.order.infrastructure.fake;

import com.github.rhafaelcosta.commerce.order.domain.model.service.ShippingCostService;
import com.github.rhafaelcosta.commerce.order.domain.model.valueobject.Money;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@ConditionalOnProperty(name = "commerce-platform.integrations.shipping.provider", havingValue = "FAKE")
public class ShippingCostServiceFakeImpl implements ShippingCostService {

    @Override
    public CalculationResult calculate(CalculationRequest calculationRequest) {
        return CalculationResult.builder()
                .cost(new Money("20"))
                .expectedDate(LocalDate.now().plusDays(5))
                .build();
    }

}
