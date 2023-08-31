package com.project.spring.service.paypal;

import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.PurchaseUnitRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class OrderRequestBuilder {
    public OrderRequest buildOrderRequestBody(String currencyCode, String amount) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown().currencyCode(currencyCode).value(amount);
        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest().amountWithBreakdown(amountWithBreakdown);

        orderRequest.purchaseUnits(Collections.singletonList(purchaseUnit));

        return orderRequest;
    }
}
