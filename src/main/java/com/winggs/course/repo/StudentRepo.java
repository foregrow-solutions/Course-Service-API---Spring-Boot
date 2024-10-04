package com.winggs.course.repo;

import com.winggs.course.modal.entity.School;
import com.winggs.course.modal.entity.Student;
import com.winggs.course.modal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, String> {
    Page<Student> findAllBySchool(School school,
                                  Pageable pageable);
    List<Student> findAllBySchool(School school);

    Optional<Student> findTopByUser(User user);

}
