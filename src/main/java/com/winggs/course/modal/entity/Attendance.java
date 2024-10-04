package com.winggs.course.modal.entity;

import com.winggs.course.modal.enums.AttendanceStatus;
import com.winggs.course.modal.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "school_id")
    School school;
    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    LocalDate date;

    @Column(name = "in_time")
    private LocalTime in;

    @Column(name = "out_time")
    private LocalTime out;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    Facility facility;

    @Enumerated(EnumType.STRING)
    AttendanceStatus status;
}
