package com.winggs.course.service.impl;

import com.winggs.course.modal.dto.StudentDto;
import com.winggs.course.modal.dto.StudentResultDto;
import com.winggs.course.modal.entity.Result;
import com.winggs.course.modal.entity.School;
import com.winggs.course.modal.entity.Student;
import com.winggs.course.modal.entity.User;
import com.winggs.course.modal.enums.Role;
import com.winggs.course.repo.*;
import com.winggs.course.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final UserRepo userRepo;
    private final StudentRepo studentRepo;
    private final ResultRepo resultRepo;
    private final SchoolRepo schoolRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;
    private final StudentAnswerRepo studentAnswerRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public StudentDto addStudent(String schoolId, StudentDto studentDto) {
        School school = schoolRepo.getReferenceById(schoolId);

        var save = userRepo.findUserByEmail(studentDto.getEmail()).orElseGet(() -> {
            User user = new User();
            user.setSchool(school);
            user.setFirstName(studentDto.getFirstName());
            user.setLastName(studentDto.getLastName());
            user.setMobile(studentDto.getMobile());
            user.setEmail(studentDto.getEmail());
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(Role.STUDENT);
            return userRepo.save(user);
        });

        Student student = new Student();
        student.setUser(save);
        student.setPermitNo(studentDto.getPermitNumber());
        student.setDob(studentDto.getDob());
        student.setFee(studentDto.getFee());
        student.setPermitNo(studentDto.getPermitNumber());
        student.setGender(studentDto.getGender());
        student.setSchool(school);
        student.setAddress(studentDto.getAddress());
        student.setCreatedDate(Instant.now());
        student.setModifiedDate(Instant.now());
        Student save1 = studentRepo.save(student);
        return StudentDto.of(save1);
    }

    @Override
    @Transactional
    public Optional<StudentDto> updateStudent(String schoolId, String id, StudentDto studentDto) {
        Optional<School> school = schoolRepo.findById(schoolId);
        var studentDetail = studentRepo.findById(id).map(student -> {
            userRepo.findById(student.getUser().getId()).map(user1 -> {
                user1.setFirstName(studentDto.getFirstName());
                user1.setLastName(studentDto.getLastName());
                user1.setEmail(studentDto.getEmail());
                user1.setMobile(studentDto.getMobile());
                return user1;
            });
            student.setDob(studentDto.getDob());
            student.setGender(studentDto.getGender());
            student.setPermitNo(studentDto.getPermitNumber());
            student.setFee(studentDto.getFee());
            student.setAddress(studentDto.getAddress());
            student.setModifiedDate(Instant.now());
            return studentRepo.save(student);
        });
        return Optional.of(StudentDto.of(studentDetail.get()));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDto> get(String id) {
        return studentRepo.findById(id).map(StudentDto::of);
    }

    @Override
    public Page<StudentDto> getAll(String schoolId, Pageable pageable) {
        return studentRepo.findAllBySchool(schoolRepo.getReferenceById(schoolId), pageable).map(StudentDto::of);
    }

    @Override
    public Page<StudentDto> getAllStudent(Pageable pageable) {
        return studentRepo.findAll(pageable).map(StudentDto::of);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getSchoolStudentList(String schoolId) {
        return studentRepo.findAllBySchool(schoolRepo.getReferenceById(schoolId)).stream().map(StudentDto::of).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentResultDto updateStudentScore(String userId, StudentResultDto resultDto) {
        Result result = new Result();
        result.setUser(userRepo.getReferenceById(userId));
        result.setScore(resultDto.getScore());
        result.setCreatedDate(Instant.now());
        Result save = resultRepo.save(result);
        return StudentResultDto.of(save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResultDto> getAllStudentScore(String userId) {
        return resultRepo.findAllByUser(userRepo.getReferenceById(userId), Sort.by(Sort.Direction.DESC, "createdDate"))
                .stream().map(StudentResultDto::of).collect(Collectors.toList());
    }

    @Override
    public Optional<StudentResultDto> getStudentScore(int id) {
        return resultRepo.findById(id).map(StudentResultDto::of);
    }

    @Override
    @Transactional
    public StudentResultDto submitQuiz(String id, List<Integer> answer) {
        //TODO : Check answer is available
        User user = userRepo.getReferenceById(id);
        AtomicInteger num = new AtomicInteger();
        answer.forEach(answerId -> {
            Boolean isTrue = answerRepo.getReferenceById(answerId).getIsTrue();
            if (isTrue) {
                num.addAndGet(1);
            }
        });
        return saveScore(user, num.get());
    }

    @Override
    @Transactional
    public StudentResultDto saveScore(User user, int score) {
        Result result = new Result();
        result.setUser(user);
        result.setScore(score);
        result.setCreatedDate(Instant.now());
        Result save = resultRepo.save(result);
        return StudentResultDto.of(save);
    }
}
