package com.winggs.course.modal.entity;

import com.winggs.course.modal.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    Payment payment;

    @Enumerated(EnumType.STRING)
    TransactionStatus status;

    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant modifiedDate;
}
