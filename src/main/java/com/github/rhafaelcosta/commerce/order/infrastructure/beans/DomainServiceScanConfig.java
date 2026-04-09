package com.github.rhafaelcosta.commerce.order.infrastructure.beans;

import com.github.rhafaelcosta.commerce.order.domain.model.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.github.rhafaelcosta.commerce.order.domain.model",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = DomainService.class
        )
)
public class DomainServiceScanConfig {
}
