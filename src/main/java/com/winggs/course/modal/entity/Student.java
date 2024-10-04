package com.winggs.course.modal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.winggs.course.modal.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Student {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    School school;

    @ManyToMany(mappedBy = "students")
    @JsonBackReference
    List<Facility> facilities;

    private String permitNo;
    private Date dob;
    private String permitUrl;
    private String address;
    private Double fee;

    @Enumerated(EnumType.STRING)
    Gender gender;
    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant modifiedDate;
}
