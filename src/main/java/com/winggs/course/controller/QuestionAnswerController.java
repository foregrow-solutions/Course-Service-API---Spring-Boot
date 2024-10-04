package com.winggs.course.controller;

import com.winggs.course.modal.dto.QuestionAndAnswerDetailsDto;
import com.winggs.course.modal.dto.QuestionAndAnswerDto;
import com.winggs.course.modal.dto.QuestionDto;
import com.winggs.course.modal.payload.CreateQuestionPayload;
import com.winggs.course.service.QuestionAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Question Answer APIs", description = "API for manage Question And Answer Operations")
@RequiredArgsConstructor
@Slf4j
@RestController
public class QuestionAnswerController {
    private final QuestionAnswerService questionAnswerService;

    //Todo: make separate end point for Question and Answers

    @Operation(summary = "Added New Questions And Answers")
    @PostMapping("/admin/questions")
    public QuestionAndAnswerDto addNewQA(@RequestBody CreateQuestionPayload payload) {
        return questionAnswerService.addQuestion(payload);
    }

    @Operation(summary = "Update Given Question Details")
    @PutMapping("/admin/questions/{questionId}")
    public Optional<QuestionDto> updateQA(@PathVariable int chapterId,
                                          @PathVariable int questionId,
                                          @RequestBody QuestionAndAnswerDto questionAndAnswerDto) {
        return questionAnswerService.updateQuestion(questionId, questionAndAnswerDto);
    }

    @Operation(summary = "Get Given Question Details")
    @GetMapping(value = {"/admin/questions/{questionId}", "/questions/{questionId}"})
    Optional<QuestionAndAnswerDetailsDto> getDetails(@PathVariable int questionId) {
        return questionAnswerService.getQuestion(questionId);
    }

    @Operation(summary = "Get All quiz questions")
    @GetMapping("/quiz")
    Page<QuestionDto> getQuiz(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                              Pageable pageable) {
        return questionAnswerService.getAllQuestion(pageable);
    }

}
