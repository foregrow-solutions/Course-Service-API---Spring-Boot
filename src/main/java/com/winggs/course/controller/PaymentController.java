package com.winggs.course.controller;

import com.stripe.exception.StripeException;
import com.winggs.course.modal.payload.CreatePaymentPayload;
import com.winggs.course.security.AuthenticatedUser;
import com.winggs.course.security.CurrentUser;
import com.winggs.course.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment APIs", description = "API for manage Payment Operations")
@Slf4j
@RequiredArgsConstructor
@RestController
public class PaymentController {
    @Value("${stripe.credentials.secret-key}")
    String secretKey;
    public final PaymentService paymentService;

    @Operation(summary = "Payment Given Course")
    @PostMapping("/payment")
    public String pay(@RequestBody CreatePaymentPayload payload,
                      @CurrentUser AuthenticatedUser user) throws StripeException {
        return paymentService.pay(user.getUsername(), payload);
    }

    @GetMapping("/key")
    public void verifyKey() {
        log.info("key is : {}", secretKey);
    }
}
