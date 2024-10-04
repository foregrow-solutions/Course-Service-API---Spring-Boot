package com.winggs.course.service;

import com.stripe.exception.StripeException;
import com.winggs.course.modal.payload.CreatePaymentPayload;

public interface PaymentService {
    public String pay(String userId, CreatePaymentPayload payload) throws StripeException;

}
