package com.winggs.course.modal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String name;

    @OneToMany
    @JoinColumn(name = "answer_id")
    @JsonManagedReference
    Set<Answer> answers;

}
