package com.winggs.course.repo;

import com.winggs.course.modal.entity.School;
import com.winggs.course.modal.entity.Facility;
import com.winggs.course.modal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepo extends JpaRepository<Facility, Integer> {

    List<Facility> findAllBySchool(School school);

    Facility findByUser(User user);

    Page<Facility> findAllBySchool(School school, Pageable pageable);
}
