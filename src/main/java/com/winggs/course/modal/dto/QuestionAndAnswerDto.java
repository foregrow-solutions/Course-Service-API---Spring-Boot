package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.Answer;
import com.winggs.course.modal.entity.Question;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
public class QuestionAndAnswerDto {
    int id;
    String name;
    List<AnswerDto> answers;


    public static QuestionAndAnswerDto of(Question question, List<Answer> answerList) {
        QuestionAndAnswerDto output = new QuestionAndAnswerDto();
        output.setId(question.getId());
        output.setName(question.getName());
        output.setAnswers(answerList.stream().map(AnswerDto::of).collect(Collectors.toList()));
        return output;
    }
}
