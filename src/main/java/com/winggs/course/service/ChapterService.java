package com.winggs.course.service;

import com.winggs.course.modal.dto.ChapterDto;
import com.winggs.course.modal.dto.ChapterSectionDetailDto;
import com.winggs.course.modal.dto.ChapterSectionDto;
import com.winggs.course.modal.dto.ChapterSectionListDto;
import com.winggs.course.modal.payload.CreateChapterPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChapterService {

    ChapterDto create(CreateChapterPayload payload);

    Optional<ChapterDto> get(int chapterId);

    List<ChapterDto> getAll();

    Optional<ChapterDto> update(int chapterId, ChapterDto chapterDto);

    ChapterSectionDto addSection(int chapterId, ChapterSectionDto chapterSectionDto);

    Optional<ChapterSectionDto> updateSection(int chapterId, int sectionId, ChapterSectionDto chapterSectionDto);

    Optional<ChapterSectionDto> getSectionDetail(int chapterId, int sectionId);
    Optional<ChapterSectionDto> getSectionByIndex(String indexNumber);

    Optional<ChapterSectionDetailDto> getSectionById(int sectionId);

    List<ChapterSectionListDto> getAllSection(int chapterId);

    Page<ChapterSectionDto> getAllChapterSection(int chapterId, Pageable pageable);

    void deleteChapterSection(int sectionId);

}
