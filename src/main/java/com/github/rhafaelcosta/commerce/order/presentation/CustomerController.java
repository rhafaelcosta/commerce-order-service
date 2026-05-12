package com.github.rhafaelcosta.commerce.order.presentation;

import com.github.rhafaelcosta.commerce.order.application.customer.management.CustomerInput;
import com.github.rhafaelcosta.commerce.order.application.customer.management.CustomerManagementApplicationService;
import com.github.rhafaelcosta.commerce.order.application.customer.management.CustomerUpdateInput;
import com.github.rhafaelcosta.commerce.order.application.customer.query.CustomerFilter;
import com.github.rhafaelcosta.commerce.order.application.customer.query.CustomerOutput;
import com.github.rhafaelcosta.commerce.order.application.customer.query.CustomerQueryService;
import com.github.rhafaelcosta.commerce.order.application.customer.query.CustomerSummaryOutput;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerQueryService customerQueryService;
    private final CustomerManagementApplicationService customerManagementApplicationService;

    @GetMapping
    public PageModel<CustomerSummaryOutput> findAll(CustomerFilter customerFilter) {
        return PageModel.of(customerQueryService.filter(customerFilter));
    }

    @GetMapping("/{id}")
    public CustomerOutput findById(@PathVariable UUID id) {
        return customerQueryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOutput create(@RequestBody @Valid CustomerInput input, HttpServletResponse httpServletResponse) {
        UUID customerId = customerManagementApplicationService.create(input);

        UriComponentsBuilder builder = fromMethodCall(on(CustomerController.class).findById(customerId));
        httpServletResponse.addHeader("Location", builder.toUriString());

        return customerQueryService.findById(customerId);
    }

    @PutMapping("/{id}")
    public CustomerOutput update(@PathVariable UUID id, @RequestBody @Valid CustomerUpdateInput input) {
        customerManagementApplicationService.update(id, input);
        return customerQueryService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        customerManagementApplicationService.archive(id);
    }

}