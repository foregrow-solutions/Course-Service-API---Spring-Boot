package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.Answer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {
    int id;
    String title;

    public static AnswerDto of(Answer answer) {
        AnswerDto output = new AnswerDto();
        output.setId(answer.getId());
        output.setTitle(answer.getName());
        return output;
    }
}
