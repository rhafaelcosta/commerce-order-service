package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.web.customer;

import com.github.rhafaelcosta.commerce.order.core.ports.in.customer.*;
import com.github.rhafaelcosta.commerce.order.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.github.rhafaelcosta.commerce.order.core.ports.in.shoppingcart.ShoppingCartOutput;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.web.PageModel;
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

    private final ForManagingCustomers forManagingCustomers;
    private final ForQueryingCustomers forQueryingCustomers;
    private final ForQueryingShoppingCarts forQueryingShoppingCarts;

    @GetMapping
    public PageModel<CustomerSummaryOutput> findAll(CustomerFilter customerFilter) {
        return PageModel.of(forQueryingCustomers.filter(customerFilter));
    }

    @GetMapping("/{id}")
    public CustomerOutput findById(@PathVariable UUID id) {
        return forQueryingCustomers.findById(id);
    }

    @GetMapping("/{id}/shopping-cart")
    public ShoppingCartOutput findShoppingCartByCustomerId(@PathVariable UUID id) {
        return forQueryingShoppingCarts.findByCustomerId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOutput create(@RequestBody @Valid CustomerInput input, HttpServletResponse httpServletResponse) {
        UUID customerId = forManagingCustomers.create(input);

        UriComponentsBuilder builder = fromMethodCall(on(CustomerController.class).findById(customerId));
        httpServletResponse.addHeader("Location", builder.toUriString());

        return forQueryingCustomers.findById(customerId);
    }

    @PutMapping("/{id}")
    public CustomerOutput update(@PathVariable UUID id, @RequestBody @Valid CustomerUpdateInput input) {
        forManagingCustomers.update(id, input);
        return forQueryingCustomers.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        forManagingCustomers.archive(id);
    }

}