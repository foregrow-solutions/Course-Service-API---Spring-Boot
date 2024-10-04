package com.winggs.course.repo;

import com.winggs.course.modal.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
}
