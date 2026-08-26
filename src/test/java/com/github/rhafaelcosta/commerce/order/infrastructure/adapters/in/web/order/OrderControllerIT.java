package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.web.order;

import com.github.rhafaelcosta.commerce.order.core.application.checkout.BuyNowInputTestDataBuilder;
import com.github.rhafaelcosta.commerce.order.core.domain.model.order.OrderId;
import com.github.rhafaelcosta.commerce.order.core.ports.in.checkout.BuyNowInput;
import com.github.rhafaelcosta.commerce.order.core.ports.out.orders.OrderDetailOutput;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.web.AbstractPresentationIT;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.customer.CustomerPersistenceEntityRepository;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.order.OrderPersistenceEntityRepository;
import com.github.rhafaelcosta.commerce.order.infrastructure.adapters.out.persistence.shoppingcart.ShoppingCartPersistenceEntityRepository;
import com.github.rhafaelcosta.commerce.order.utils.CommercePlaftformResourceUtils;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

class OrderControllerIT extends AbstractPresentationIT {

    @Autowired
    private OrderPersistenceEntityRepository orderRepository;

    private static final UUID validProductId = UUID.fromString("fffe6ec2-7103-48b3-8e4f-3b58e43fb75a");
    private static final UUID validCustomerId = UUID.fromString("6e148bd5-47f6-4022-b9da-07cfaa294f7a");

    @BeforeEach
    void setup() {
        super.beforeEach();
    }

    @BeforeAll
    static void setupBeforeAll() {
        AbstractPresentationIT.initWireMock();
    }

    @AfterAll
    static void afterAll() {
        AbstractPresentationIT.stopMock();
    }

    @Test
    @Order(1)
    void shouldCreateOrderUsingProduct() {
        String json = CommercePlaftformResourceUtils.readContent("json/create-order-with-product.json");

        String createdOrderId = RestAssured
            .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(json)
            .when()
                .post("/api/v1/orders")
            .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.not(Matchers.emptyString()),
                        "customer.id", Matchers.is(validCustomerId.toString()))
            .extract()
                .jsonPath().getString("id");

        boolean orderExists = orderRepository.existsById(new OrderId(createdOrderId).value().toLong());
        Assertions.assertThat(orderExists).isTrue();

    }

    @Test
    @Order(2)
    void shouldCreateOrderUsingProduct_DTO() {
        UUID creditCardId = UUID.randomUUID();
        BuyNowInput input = BuyNowInputTestDataBuilder.aBuyNowInput()
                .productId(validProductId)
                .customerId(validCustomerId)
                .creditCardId(creditCardId)
                .build();

        OrderDetailOutput orderDetailOutput = RestAssured
            .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(input)
            .when()
                .post("/api/v1/orders")
            .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.not(Matchers.emptyString()),
                        "customer.id", Matchers.is(validCustomerId.toString()))
            .extract()
                .body().as(OrderDetailOutput.class);

        Assertions.assertThat(orderDetailOutput.getCreditCardId()).isEqualTo(creditCardId);
        Assertions.assertThat(orderDetailOutput.getCustomer().getId()).isEqualTo(validCustomerId);

        boolean orderExists = orderRepository.existsById(new OrderId(orderDetailOutput.getId()).value().toLong());
        Assertions.assertThat(orderExists).isTrue();
    }

    @Test
    @Order(3)
    void shouldNotCreateOrderUsingProductWhenProductNotExists() {
        String json = CommercePlaftformResourceUtils.readContent("json/create-order-with-invalid-product.json");

        RestAssured
            .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(json)
            .when()
                .post("/api/v1/orders")
            .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());

    }

    @Test
    @Order(4)
    void shouldNotCreateOrderUsingProductWhenCustomerWasNotFound() {
        String json = CommercePlaftformResourceUtils.readContent("json/create-order-with-product-and-invalid-customer.json");
        RestAssured
            .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(json)
            .when()
                .post("/api/v1/orders")
            .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    @Order(5)
    void shouldCreateOrderUsingShoppingCart() {
        String json = CommercePlaftformResourceUtils.readContent("json/create-order-with-shopping-cart.json");

        OrderDetailOutput orderDetailOutput = RestAssured
            .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-shopping-cart.v1+json")
                .body(json)
            .when()
                .post("/api/v1/orders")
            .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.not(Matchers.emptyString()),
                        "customer.id", Matchers.is(validCustomerId.toString()))
            .extract()
                .body().as(OrderDetailOutput.class);

        Assertions.assertThat(orderDetailOutput.getCustomer().getId()).isEqualTo(validCustomerId);

        boolean orderExists = orderRepository.existsById(new OrderId(orderDetailOutput.getId()).value().toLong());
        Assertions.assertThat(orderExists).isTrue();
    }

}
