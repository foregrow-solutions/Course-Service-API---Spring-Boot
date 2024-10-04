package com.winggs.course.repo;

import com.winggs.course.modal.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepo extends JpaRepository<School, String> {
}
