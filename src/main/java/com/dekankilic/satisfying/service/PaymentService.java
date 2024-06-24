package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.dto.response.PaymentResponse;
import com.dekankilic.satisfying.model.Order;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${stripe.api-key}")
    private String stripeApiKey;

    public PaymentResponse createPaymentLink(Order order) { // For the order, it will create the payment link

        Stripe.apiKey = stripeApiKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment/success/" + order.getId())
                .setCancelUrl("http://localhost:300/payment/fail")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmountDecimal(order.getTotalAmount())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("satisfying")
                                        .build())
                                .build())
                        .build())
                .build();

        try{
            Session session = Session.create(params);
            return new PaymentResponse(session.getUrl());
        }catch (StripeException ex) {
            throw new RuntimeException(ex);
        }

    }
}
