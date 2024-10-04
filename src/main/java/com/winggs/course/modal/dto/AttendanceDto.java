package com.winggs.course.modal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.winggs.course.modal.entity.Attendance;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceDto {
    int id;
    String studentId;
    LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", lenient = OptBoolean.TRUE)
    LocalTime in;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", lenient = OptBoolean.TRUE)
    LocalTime out;

    public static AttendanceDto of(Attendance attendance) {
        AttendanceDto output = new AttendanceDto();
        output.setId(attendance.getId());
        output.setStudentId(attendance.getStudent().getId());
        output.setDate(attendance.getDate());
        output.setIn(attendance.getIn());
        output.setOut(attendance.getOut());
        return output;
    }
}
