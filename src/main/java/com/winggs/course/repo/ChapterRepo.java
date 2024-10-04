package com.winggs.course.repo;

import com.winggs.course.modal.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepo extends JpaRepository<Chapter, Integer> {
}
