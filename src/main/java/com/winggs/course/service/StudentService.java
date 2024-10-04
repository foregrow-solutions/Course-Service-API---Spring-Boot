package com.winggs.course.service;

import com.winggs.course.modal.dto.StudentDto;
import com.winggs.course.modal.dto.StudentResultDto;
import com.winggs.course.modal.entity.User;
import com.winggs.course.modal.payload.StudentQuizPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public StudentDto addStudent(String schoolId, StudentDto studentDto);

    public Optional<StudentDto> updateStudent(String schoolId, String id, StudentDto studentDto);

    public Optional<StudentDto> get(String id);

    public Page<StudentDto> getAll(String schoolId, Pageable pageable);

    Page<StudentDto> getAllStudent(Pageable pageable);

    List<StudentDto> getSchoolStudentList(String schoolId);

    StudentResultDto updateStudentScore(String userId, StudentResultDto resultDto);

    List<StudentResultDto> getAllStudentScore(String userId);
    Optional<StudentResultDto> getStudentScore( int id);

    StudentResultDto submitQuiz(String userId, List<Integer> Answers);

    StudentResultDto saveScore(User user, int score);
}
