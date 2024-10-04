package com.winggs.course.service;

import com.winggs.course.modal.dto.QuestionAndAnswerDetailsDto;
import com.winggs.course.modal.dto.QuestionAndAnswerDto;
import com.winggs.course.modal.dto.QuestionDto;
import com.winggs.course.modal.entity.User;
import com.winggs.course.modal.payload.CreateQuestionPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionAnswerService {
    QuestionAndAnswerDto addQuestion(CreateQuestionPayload payload);

    Optional<QuestionDto> updateQuestion(int questionId, QuestionAndAnswerDto questionAndAnswerDto);

    Optional<QuestionAndAnswerDetailsDto> getQuestion(int questionId);

    Page<QuestionDto> getAllQuestion(Pageable pageable);

    void markedAnswer(String userId, int question, int answer);

    String calculate(String userId);
}
