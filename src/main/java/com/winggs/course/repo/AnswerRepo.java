package com.winggs.course.repo;

import com.winggs.course.modal.entity.Answer;
import com.winggs.course.modal.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Integer> {
    List<Answer> findAllByQuestion(Question question);
}
