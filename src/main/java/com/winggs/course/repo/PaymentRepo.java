package com.winggs.course.repo;

import com.winggs.course.modal.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
}
