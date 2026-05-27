package com.github.rhafaelcosta.commerce.order.presentation;

import com.github.rhafaelcosta.commerce.order.application.checkout.BuyNowApplicationService;
import com.github.rhafaelcosta.commerce.order.application.checkout.BuyNowInput;
import com.github.rhafaelcosta.commerce.order.application.checkout.CheckoutApplicationService;
import com.github.rhafaelcosta.commerce.order.application.checkout.CheckoutInput;
import com.github.rhafaelcosta.commerce.order.application.order.query.OrderDetailOutput;
import com.github.rhafaelcosta.commerce.order.application.order.query.OrderFilter;
import com.github.rhafaelcosta.commerce.order.application.order.query.OrderQueryService;
import com.github.rhafaelcosta.commerce.order.application.order.query.OrderSummaryOutput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderQueryService orderQueryService;
    private final CheckoutApplicationService checkoutApplicationService;
    private final BuyNowApplicationService buyNowApplicationService;

    @GetMapping
    public PageModel<OrderSummaryOutput> filter(OrderFilter filter) {
        return PageModel.of(orderQueryService.filter(filter));
    }

    @GetMapping("/{orderId}")
    public OrderDetailOutput findById(@PathVariable String orderId) {
        return orderQueryService.findById(orderId);
    }

    @PostMapping(consumes = "application/vnd.order-with-product.v1+json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailOutput createWithProduct(@Valid @RequestBody BuyNowInput input) {
        String orderId = buyNowApplicationService.buyNow(input);
        return orderQueryService.findById(orderId);
    }

    @PostMapping(consumes = "application/vnd.order-with-shopping-cart.v1+json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailOutput createWithShoppingCart(@Valid @RequestBody CheckoutInput input) {
        String orderId = checkoutApplicationService.checkout(input);
        return orderQueryService.findById(orderId);
    }

}