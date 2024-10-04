package com.winggs.course.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.winggs.course.modal.entity.Payment;
import com.winggs.course.modal.entity.Transaction;
import com.winggs.course.modal.entity.User;
import com.winggs.course.modal.enums.TransactionStatus;
import com.winggs.course.modal.payload.CreatePaymentPayload;
import com.winggs.course.repo.PaymentRepo;
import com.winggs.course.repo.StudentRepo;
import com.winggs.course.repo.TransactionRepo;
import com.winggs.course.repo.UserRepo;
import com.winggs.course.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${stripe.credentials.public-key}")
    String publicKey;

    @Value("${stripe.credentials.secret-key}")
    String secretKey;
    private final UserRepo userRepo;
    private final StudentRepo studentRepo;
    private final PaymentRepo paymentRepo;
    private final TransactionRepo transactionRepo;

    @Override
    public String pay(String userId, CreatePaymentPayload payload) throws StripeException {
        User user = userRepo.getReferenceById(userId);
        studentRepo.findTopByUser(user).map(student -> {
            student.setFee(payload.price());
            return student;
        });
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPrice(payload.price());
        payment.setCreatedDate(Instant.now());
        Payment save = paymentRepo.save(payment);
        log.info("Card Payload : {}", payload);

        String token = createCardToken(payload.cardNumber(), payload.expMonth(), payload.expYear(), payload.csv());
        log.info("Token : {}", token);


        HashMap<String, Object> charge = new HashMap<>();
        charge.put("amount", save.getPrice().intValue());
        charge.put("currency", "usd");
        charge.put("description", "Example charge");
        charge.put("source", token);
        Charge charge1 = Charge.create(charge);

        log.info("Charge Response : {}", charge1);

        Transaction transaction = new Transaction();
        transaction.setPayment(save);
        if (charge1.getPaid()) {
            transaction.setStatus(TransactionStatus.SUCCESS);
        } else {
            transaction.setStatus(TransactionStatus.FAIL);
        }
        transaction.setCreatedDate(Instant.now());
        Transaction save1 = transactionRepo.save(transaction);
        return null;
    }

    public String createCardToken(String cardNumber, String expMonth, String expYear,
                                  String csv) throws StripeException {
        Stripe.apiKey = secretKey;

        Map<String, Object> card = new HashMap<>();
        card.put("number", cardNumber);
        card.put("exp_month", expMonth);
        card.put("exp_year", expYear);
        card.put("cvc", csv);

        Map<String, Object> params = new HashMap<>();
        params.put("card", card);
        Token token = Token.create(params);

        log.info("Credit Card Generated Token : {}", token);
        return token.getId();
    }
}
