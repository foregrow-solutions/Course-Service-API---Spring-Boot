package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.Question;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDto {
    int id;
    String name;

    public static QuestionDto of(Question question) {
        QuestionDto output = new QuestionDto();
        output.setId(question.getId());
        output.setName(question.getName());
        return output;
    }
}
