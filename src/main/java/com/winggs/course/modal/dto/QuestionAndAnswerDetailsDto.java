package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.Answer;
import com.winggs.course.modal.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class QuestionAndAnswerDetailsDto {
    int id;
    String name;
    List<AnswerDto> answers;
    int nextId;

    public static QuestionAndAnswerDetailsDto of(Question question, List<Answer> answers, int next) {
        QuestionAndAnswerDetailsDto output = new QuestionAndAnswerDetailsDto();
        output.setId(question.getId());
        output.setName(question.getName());
        output.setAnswers(answers.stream().map(AnswerDto::of).collect(Collectors.toList()));
        output.setNextId(next);
        return output;
    }

}
