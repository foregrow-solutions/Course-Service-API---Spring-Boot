package com.winggs.course.modal.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
public class StudentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    Double score;

    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant modifiedDate;
}
