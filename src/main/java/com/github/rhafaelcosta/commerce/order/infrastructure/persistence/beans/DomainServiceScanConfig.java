package com.github.rhafaelcosta.commerce.order.infrastructure.persistence.beans;

import com.github.rhafaelcosta.commerce.order.domain.model.utility.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.github.rhafaelcosta.commerce.order.domain.model.service",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = DomainService.class
        )
)
public class DomainServiceScanConfig {
}
