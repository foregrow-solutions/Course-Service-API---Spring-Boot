package com.winggs.course.repo;

import com.winggs.course.modal.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepo extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT MIN(q.id) FROM Question q WHERE q.id > :id")
    Integer getNextId(@Param("id") int id);
}
