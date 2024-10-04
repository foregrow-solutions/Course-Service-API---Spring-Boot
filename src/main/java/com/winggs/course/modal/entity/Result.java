package com.winggs.course.modal.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    int score;

    @CreatedDate
    Instant createdDate;
}
