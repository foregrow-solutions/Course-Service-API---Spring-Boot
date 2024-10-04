package com.winggs.course.service.impl;

import com.winggs.course.modal.dto.QuestionAndAnswerDetailsDto;
import com.winggs.course.modal.dto.QuestionAndAnswerDto;
import com.winggs.course.modal.dto.QuestionDto;
import com.winggs.course.modal.entity.*;
import com.winggs.course.modal.payload.CreateQuestionPayload;
import com.winggs.course.repo.*;
import com.winggs.course.service.QuestionAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    private final ChapterRepo chapterRepo;
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;
    private final StudentAnswerRepo studentAnswerRepo;

    @Override
    @Transactional
    public QuestionAndAnswerDto addQuestion(CreateQuestionPayload payload) {
        Question question = new Question();
        question.setName(payload.question());
        Question save = questionRepo.save(question);

        payload.answers().forEach(createAnswerPayload -> {
            Answer answer = new Answer();
            answer.setQuestion(save);
            answer.setName(createAnswerPayload.title());
            answer.setIsTrue(createAnswerPayload.isTrue());
            Answer save1 = answerRepo.save(answer);
        });

        List<Answer> all = answerRepo.findAllByQuestion(save);

        return QuestionAndAnswerDto.of(save, all);
    }

    @Override
    @Transactional
    public Optional<QuestionDto> updateQuestion(int questionId, QuestionAndAnswerDto questionAndAnswerDto) {
        return questionRepo.findById(questionId).map(question -> {
            question.setName(questionAndAnswerDto.getName());
            return QuestionDto.of(question);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionAndAnswerDetailsDto> getQuestion(int questionId) {
        Optional<Question> question = questionRepo.findById(questionId);
        Integer nextId = questionRepo.getNextId(questionId);
        if (nextId == null) {
            nextId = 0;
        }
        Integer finalNextId = nextId;
        if (question.isPresent()) {
            List<Answer> allByQuestion = answerRepo.findAllByQuestion(questionRepo.getReferenceById(questionId));
            return Optional.of(QuestionAndAnswerDetailsDto.of(question.get(), allByQuestion, finalNextId));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<QuestionDto> getAllQuestion(Pageable pageable) {
        return questionRepo.findAll(pageable).map(QuestionDto::of);
    }

    @Override
    @Transactional
    public void markedAnswer(String userId, int question, int answer) {
        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setUser(userRepo.getReferenceById(userId));
        studentAnswer.setAnswer(answerRepo.getReferenceById(answer));
        studentAnswer.setQuestion(questionRepo.getReferenceById(question));
        StudentAnswer save = studentAnswerRepo.save(studentAnswer);
    }

    @Override
    public String calculate(String userId) {
        List<StudentAnswer> user = studentAnswerRepo.findAllByUser(userRepo.getReferenceById(userId));
        AtomicInteger score = new AtomicInteger();
        user.forEach(studentAnswer -> {
            Boolean isTrue = studentAnswer.getAnswer().getIsTrue();
            if (isTrue) {
                score.set(+1);
            }
        });
        return String.valueOf(score);
    }

}
