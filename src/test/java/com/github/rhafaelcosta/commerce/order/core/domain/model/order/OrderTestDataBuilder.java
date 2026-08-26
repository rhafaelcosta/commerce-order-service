package com.github.rhafaelcosta.commerce.order.core.domain.model.order;

import com.github.rhafaelcosta.commerce.order.core.domain.model.commons.*;
import com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerId;
import com.github.rhafaelcosta.commerce.order.core.domain.model.product.ProductTestDataBuilder;

import java.time.LocalDate;

import static com.github.rhafaelcosta.commerce.order.core.domain.model.customer.CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

public class OrderTestDataBuilder {

    private CustomerId customerId = DEFAULT_CUSTOMER_ID;
    private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

    private Billing billing = aBilling();
    private Shipping shipping = aShipping();

    private boolean withItems = true;
    private OrderStatus status = OrderStatus.DRAFT;
    private CreditCardId creditCardId;

    private OrderTestDataBuilder() {
    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public Order build() {
        Order order = Order.draft(customerId);
        order.changeShipping(shipping);
        order.changeBilling(billing);
        order.changePaymentMethod(paymentMethod, creditCardId);

        if (withItems) {
            order.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(2));
            order.addItem(ProductTestDataBuilder.aProductAltRamMemory().build(), new Quantity(1));
        }

        switch (this.status) {
            case DRAFT -> {
            }
            case PLACED -> order.place();
            case PAID -> {
                order.place();
                order.markAsPaid();
            }
            case READY -> {
                order.place();
                order.markAsPaid();
                order.markAsReady();
            }
            case CANCELED -> order.cancel();
        }

        return order;
    }

    public static Billing aBilling() {
        return Billing.builder()
                .address(anAddress())
                .fullName(new FullName("John", "Doe"))
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .email(new Email("jhon.doe@gmail.com"))
                .build();
    }

    public static Shipping aShipping() {
        return Shipping.builder()
                .address(anAddress())
                .cost(new Money("10"))
                .expectedDate(LocalDate.now().plusWeeks(1))
                .recipient(Recipient.builder()
                        .fullName(new FullName("Jane", "Doe"))
                        .document(new Document("112-33-2321"))
                        .phone(new Phone("111-441-1244"))
                        .build()
                )
                .build();
    }

    public static Shipping aShippingAlt() {
        return Shipping.builder()
                .address(anAddressAlt())
                .cost(new Money("20.00"))
                .expectedDate(LocalDate.now().plusWeeks(2))
                .recipient(Recipient.builder()
                        .fullName(new FullName("John", "Doe"))
                        .document(new Document("552-11-4333"))
                        .phone(new Phone("54-454-1144"))
                        .build()
                )
                .build();
    }

    public static Address anAddress() {
        return Address.builder()
                .street("Bourbon Street")
                .number("1234")
                .neighborhood("North Ville")
                .complement("apt. 11")
                .city("Montfort")
                .state("South Carolina")
                .zipCode(new ZipCode("79911"))
                .build();
    }

    public static Address anAddressAlt() {
        return Address.builder()
                .street("Sansome Street")
                .number("875")
                .neighborhood("Sansome")
                .city("San Francisco")
                .state("California")
                .zipCode(new ZipCode("08040"))
                .build();
    }

    public OrderTestDataBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderTestDataBuilder paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderTestDataBuilder shipping(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public OrderTestDataBuilder billing(Billing billing) {
        this.billing = billing;
        return this;
    }

    public OrderTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }

    public OrderTestDataBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public OrderTestDataBuilder creditCardId(CreditCardId creditCardId) {
        this.creditCardId = creditCardId;
        return this;
    }

}
