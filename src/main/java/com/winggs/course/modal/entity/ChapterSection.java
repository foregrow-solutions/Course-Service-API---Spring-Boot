package com.winggs.course.modal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ChapterSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "chapter_id")
    Chapter chapter;

    private String indexNumber;
    private String title;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String descriptionContext;
}
