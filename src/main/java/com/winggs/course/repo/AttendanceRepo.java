package com.winggs.course.repo;

import com.winggs.course.modal.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
}
