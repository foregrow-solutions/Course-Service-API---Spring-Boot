package com.winggs.course.repo;

import com.winggs.course.modal.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<Session, Integer> {
}
