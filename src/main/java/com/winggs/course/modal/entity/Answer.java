package com.winggs.course.modal.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;
    private String name;
    private Boolean isTrue;
}
