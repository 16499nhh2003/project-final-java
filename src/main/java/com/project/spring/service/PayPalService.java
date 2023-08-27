package com.project.spring.service;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.project.spring.paypal.OrdersCreateResponse;
import com.project.spring.service.paypal.OrderRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PayPalService {
    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    private final OrderRequestBuilder orderRequestBuilder;

    @Autowired
    public PayPalService(OrderRequestBuilder orderRequestBuilder) {
        this.orderRequestBuilder = orderRequestBuilder;
    }

    public String createPayment(String currencyCode, BigDecimal total) {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
        PayPalHttpClient client = new PayPalHttpClient(environment);

        OrdersCreateRequest request = new OrdersCreateRequest();
        request.requestBody(orderRequestBuilder.buildOrderRequestBody(currencyCode, String.valueOf(total)));

        try {
            HttpResponse<Order> response = client.execute(request);
            if (response.statusCode() == 201) {
                String approvalUrl = response.result().links().stream()
                        .filter(link -> "approve".equals(link.rel()))
                        .findFirst()
                        .map(LinkDescription::href)
                        .orElse(null);

                return approvalUrl;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
