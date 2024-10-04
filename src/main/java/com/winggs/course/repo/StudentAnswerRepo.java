package com.winggs.course.repo;

import com.winggs.course.modal.entity.StudentAnswer;
import com.winggs.course.modal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAnswerRepo extends JpaRepository<StudentAnswer, Integer> {
    List<StudentAnswer> findAllByUser(User user);
}
