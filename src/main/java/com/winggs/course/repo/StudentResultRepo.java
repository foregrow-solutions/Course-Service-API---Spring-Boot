package com.winggs.course.repo;

import com.winggs.course.modal.entity.Student;
import com.winggs.course.modal.entity.StudentResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentResultRepo extends JpaRepository<StudentResult, Integer> {

    List<StudentResult> findAllByStudent(Student student);
}
