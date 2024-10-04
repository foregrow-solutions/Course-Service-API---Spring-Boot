package com.winggs.course.modal.payload;

public record CreatePaymentPayload(String subscribeId, Double price, String cardNumber, String expMonth, String expYear,
                                   String csv) {
}
