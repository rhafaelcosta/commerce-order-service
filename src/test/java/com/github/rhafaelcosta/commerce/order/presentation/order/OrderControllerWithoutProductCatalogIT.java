package com.github.rhafaelcosta.commerce.order.presentation.order;

import com.github.rhafaelcosta.commerce.order.infrastructure.persistence.order.OrderPersistenceEntityRepository;
import com.github.rhafaelcosta.commerce.order.presentation.AbstractPresentationIT;
import com.github.rhafaelcosta.commerce.order.utils.CommercePlaftformResourceUtils;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

class OrderControllerWithoutProductCatalogIT extends AbstractPresentationIT {

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
    @Order(6)
    @Disabled
    void shouldNotCreateOrderUsingProductWhenProductAPIIsUnavailable() {
        String json = CommercePlaftformResourceUtils.readContent("json/create-order-with-product.json");

        wireMockProductCatalog.stop();

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
                .statusCode(HttpStatus.GATEWAY_TIMEOUT.value());

    }

}
