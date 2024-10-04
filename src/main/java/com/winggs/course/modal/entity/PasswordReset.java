package com.winggs.course.modal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class PasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;
    @OneToOne(fetch = FetchType.EAGER)
    User user;

    private Date expiryDate;
}
