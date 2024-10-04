package com.winggs.course.modal.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    Answer answer;

    boolean value;
    @CreatedDate
    Instant createdDate;
}
