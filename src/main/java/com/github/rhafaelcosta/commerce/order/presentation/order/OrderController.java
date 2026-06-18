package com.github.rhafaelcosta.commerce.order.presentation.order;

import com.github.rhafaelcosta.commerce.order.application.checkout.BuyNowApplicationService;
import com.github.rhafaelcosta.commerce.order.application.checkout.BuyNowInput;
import com.github.rhafaelcosta.commerce.order.application.checkout.CheckoutApplicationService;
import com.github.rhafaelcosta.commerce.order.application.checkout.CheckoutInput;
import com.github.rhafaelcosta.commerce.order.application.order.query.OrderDetailOutput;
import com.github.rhafaelcosta.commerce.order.application.order.query.OrderFilter;
import com.github.rhafaelcosta.commerce.order.application.order.query.OrderQueryService;
import com.github.rhafaelcosta.commerce.order.application.order.query.OrderSummaryOutput;
import com.github.rhafaelcosta.commerce.order.domain.model.customer.CustomerNotFoundException;
import com.github.rhafaelcosta.commerce.order.domain.model.product.ProductNotFoundException;
import com.github.rhafaelcosta.commerce.order.domain.model.shoppingcart.ShoppingCartNotFoundException;
import com.github.rhafaelcosta.commerce.order.presentation.PageModel;
import com.github.rhafaelcosta.commerce.order.presentation.UnprocessableEntityException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/orders")
public class OrderController {

    private final OrderQueryService orderQueryService;
    private final BuyNowApplicationService buyNowApplicationService;
    private final CheckoutApplicationService checkoutApplicationService;

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
        String orderId;
        try {
            orderId = buyNowApplicationService.buyNow(input);
        } catch (CustomerNotFoundException | ProductNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
        return orderQueryService.findById(orderId);
    }

    @PostMapping(consumes = "application/vnd.order-with-shopping-cart.v1+json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailOutput createWithShoppingCart(@Valid @RequestBody CheckoutInput input) {
        String orderId;
        try {
            orderId = checkoutApplicationService.checkout(input);
        } catch (CustomerNotFoundException | ShoppingCartNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
        return orderQueryService.findById(orderId);
    }

}