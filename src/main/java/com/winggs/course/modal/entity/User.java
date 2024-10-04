package com.winggs.course.modal.entity;

import com.winggs.course.modal.enums.Gender;
import com.winggs.course.modal.enums.Role;
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
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "school_id")
    School school;

    @Enumerated(EnumType.STRING)
    Role role;

    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant modifiedDate;
}
