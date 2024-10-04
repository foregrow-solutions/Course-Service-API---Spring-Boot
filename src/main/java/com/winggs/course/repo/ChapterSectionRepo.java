package com.winggs.course.repo;

import com.winggs.course.modal.entity.Chapter;
import com.winggs.course.modal.entity.ChapterSection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChapterSectionRepo extends JpaRepository<ChapterSection, Integer> {

    @Query(value = "SELECT MIN(e.id) FROM ChapterSection e WHERE e.id > :id")
    Integer getNextId(@Param("id")int id);

    @Query(value = "SELECT MAX(e.id) FROM ChapterSection e WHERE e.id < :id")
    Integer getPreviousId(@Param("id") int id);

    Page<ChapterSection> findAllByChapter(Chapter chapter,
                                          Pageable pageable);
    List<ChapterSection> findAllByChapter(Chapter chapter);

    Optional<ChapterSection> findByIndexNumber(String index);

    Optional<ChapterSection> findByIdAndChapterId(int id, int chapter);
}
