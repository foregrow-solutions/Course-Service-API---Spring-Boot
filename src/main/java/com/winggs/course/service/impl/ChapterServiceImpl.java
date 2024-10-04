package com.winggs.course.service.impl;

import com.winggs.course.modal.dto.ChapterDto;
import com.winggs.course.modal.dto.ChapterSectionDetailDto;
import com.winggs.course.modal.dto.ChapterSectionDto;
import com.winggs.course.modal.dto.ChapterSectionListDto;
import com.winggs.course.modal.entity.Chapter;
import com.winggs.course.modal.entity.ChapterSection;
import com.winggs.course.modal.payload.CreateChapterPayload;
import com.winggs.course.repo.ChapterRepo;
import com.winggs.course.repo.ChapterSectionRepo;
import com.winggs.course.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepo chapterRepo;
    private final ChapterSectionRepo chapterSectionRepo;

    @Override
    @Transactional
    public ChapterDto create(CreateChapterPayload payload) {
        Chapter chapter = new Chapter();
        chapter.setTitle(payload.getTitle());
        Chapter save = chapterRepo.save(chapter);
        return ChapterDto.of(chapter);
    }

    @Override
    public Optional<ChapterDto> get(int chapterId) {
        return chapterRepo.findById(chapterId).map(ChapterDto::of);
    }

    @Override
    public List<ChapterDto> getAll() {
        return chapterRepo.findAll().stream().map(ChapterDto::of).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<ChapterDto> update(int chapterId, ChapterDto chapterDto) {
        return chapterRepo.findById(chapterId).map(chapter -> {
            chapter.setTitle(chapterDto.getTitle());
            chapter.setSubTitle(chapterDto.getSubTitle());
            return ChapterDto.of(chapter);
        });
    }

    @Override
    @Transactional
    public ChapterSectionDto addSection(int chapterId, ChapterSectionDto chapterSectionDto) {
        var chapterNumber = chapterRepo.findById(chapterId).orElseGet(() -> {
            Chapter chapter = new Chapter();
            return chapterRepo.save(chapter);
        });
        ChapterSection section = new ChapterSection();
        section.setChapter(chapterNumber);
        section.setIndexNumber(chapterSectionDto.getIndexNumber());
        section.setTitle(chapterSectionDto.getTitle());
        section.setDescription(chapterSectionDto.getDescription());
        section.setDescriptionContext(chapterSectionDto.getDescriptionContext());
        ChapterSection save = chapterSectionRepo.save(section);
        return ChapterSectionDto.of(save);
    }

    @Override
    @Transactional
    public Optional<ChapterSectionDto> updateSection(int chapterId, int sectionId, ChapterSectionDto chapterSectionDto) {
        return chapterSectionRepo.findById(sectionId).map(section -> {
            section.setIndexNumber(chapterSectionDto.getIndexNumber());
            section.setTitle(chapterSectionDto.getTitle());
            if (chapterSectionDto.getDescription() != null) {
                section.setDescription(chapterSectionDto.getDescription());
                section.setDescriptionContext(chapterSectionDto.getDescriptionContext());
            }
            return ChapterSectionDto.of(section);
        });
    }

    @Override
    public Optional<ChapterSectionDto> getSectionDetail(int chapterId, int sectionId) {
        Chapter chapter = chapterRepo.findById(chapterId).orElseGet(null);

        return chapterSectionRepo.findByIdAndChapterId(sectionId, chapterId).map(ChapterSectionDto::of);
    }

    @Override
    public Optional<ChapterSectionDto> getSectionByIndex(String indexNumber) {
        return chapterSectionRepo.findByIndexNumber(indexNumber).map(ChapterSectionDto::of);
    }

    @Override
    public Optional<ChapterSectionDetailDto> getSectionById(int sectionId) {
        Integer nextId = chapterSectionRepo.getNextId(sectionId);
        Integer previousId = chapterSectionRepo.getPreviousId(sectionId);
        if (nextId == null) {
            nextId = 0;
        }
        if (previousId == null) {
            previousId = 0;
        }
        Integer finalNextId = nextId;
        Integer finalPreviousId = previousId;
        return chapterSectionRepo.findById(sectionId).map(section -> ChapterSectionDetailDto.of(section, finalNextId, finalPreviousId));
    }

    @Override
    public List<ChapterSectionListDto> getAllSection(int chapterId) {
        //Todo : Sort by section indexing
        return chapterSectionRepo.findAllByChapter(chapterRepo.getReferenceById(chapterId)).stream().map(ChapterSectionListDto::of).collect(Collectors.toList());
    }

    @Override
    public Page<ChapterSectionDto> getAllChapterSection(int chapterId, Pageable pageable) {
        return chapterSectionRepo.findAllByChapter(chapterRepo.getReferenceById(chapterId), pageable).map(ChapterSectionDto::of);
    }

    @Override
    @Transactional
    public void deleteChapterSection(int sectionId) {
        if (chapterSectionRepo.existsById(sectionId)) {
            chapterSectionRepo.deleteById(sectionId);
        }
    }
}
