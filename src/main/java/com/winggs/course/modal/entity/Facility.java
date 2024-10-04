package com.winggs.course.modal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.winggs.course.modal.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "school_id"})})
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    School school;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    private String imageUrl;
    private Double experience;
    private String lastCompanyName;
    private String address;
    private String licenseNumber;
    private String licenseUrl;
    @Enumerated(EnumType.STRING)
    Gender gender;

    @CreatedDate
    Instant createdDate;

}
