package com.winggs.course.modal.dto;

import com.winggs.course.modal.constants.PassingCheckConstants;
import com.winggs.course.modal.entity.Result;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class StudentResultDto {
    private static final String PATTERN_FORMAT = "dd-MM-yyyy";
    int id;
    int score;
    String result;
    String createdDate;


    public static StudentResultDto of(Result result) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        StudentResultDto output = new StudentResultDto();
        output.setId(result.getId());
        output.setScore(result.getScore());
        output.setResult(result.getScore()>= PassingCheckConstants.MARKS ?"Pass":"Fail");
        output.setCreatedDate(formatter.format(result.getCreatedDate()));
        return output;
    }
}
