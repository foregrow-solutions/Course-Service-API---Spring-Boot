package com.winggs.course.modal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}
