package com.winggs.course.modal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String subTitle;

    @ManyToOne
    @JoinColumn(name = "section_id")
    @JsonManagedReference
    ChapterSection section;

}
