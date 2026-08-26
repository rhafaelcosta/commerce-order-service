package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.web.order;

import com.github.rhafaelcosta.commerce.order.core.ports.in.checkout.BuyNowInput;
import com.github.rhafaelcosta.commerce.order.core.ports.in.checkout.CheckoutInput;
import com.github.rhafaelcosta.commerce.order.core.ports.in.checkout.ForBuyingProduct;
import com.github.rhafaelcosta.commerce.order.core.ports.in.checkout.ForBuyingWithShoppingCart;
import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.OrderDetailOutput;
import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.OrderFilter;
import com.github.rhafaelcosta.commerce.order.core.ports.in.orders.ForQueryingOrders;
import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.OrderSummaryOutput;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerNotFoundException;
import com.github.rhafaelcosta.commerce.order.core.domain.model.product.ProductNotFoundException;
import com.github.rhafaelcosta.commerce.order.core.domain.model.shoppingcart.ShoppingCartNotFoundException;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.web.PageModel;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.web.exceptionhandler.UnprocessableEntityException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/orders")
public class OrderController {

    private final ForBuyingProduct forBuyingProduct;
    private final ForQueryingOrders forQueryingOrders;
    private final ForBuyingWithShoppingCart forBuyingWithShoppingCart;

    @GetMapping
    public PageModel<OrderSummaryOutput> filter(OrderFilter filter) {
        return PageModel.of(forQueryingOrders.filter(filter));
    }

    @GetMapping("/{orderId}")
    public OrderDetailOutput findById(@PathVariable String orderId) {
        return forQueryingOrders.findById(orderId);
    }

    @PostMapping(consumes = "application/vnd.order-with-product.v1+json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailOutput createWithProduct(@Valid @RequestBody BuyNowInput input) {
        String orderId;
        try {
            orderId = forBuyingProduct.buyNow(input);
        } catch (CustomerNotFoundException | ProductNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
        return forQueryingOrders.findById(orderId);
    }

    @PostMapping(consumes = "application/vnd.order-with-shopping-cart.v1+json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailOutput createWithShoppingCart(@Valid @RequestBody CheckoutInput input) {
        String orderId;
        try {
            orderId = forBuyingWithShoppingCart.checkout(input);
        } catch (CustomerNotFoundException | ShoppingCartNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
        return forQueryingOrders.findById(orderId);
    }

}