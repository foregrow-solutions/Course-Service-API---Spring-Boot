package com.winggs.course.modal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Entity
public class School {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    User user;

    private String dunsNo;
    private String fedTaxNo;

    private String website;

    private String address;

    private Double fee;

    @JsonManagedReference
    @OneToMany(mappedBy="school", fetch = FetchType.EAGER)
    private Set<Student> students;

    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant modifiedDate;
}
