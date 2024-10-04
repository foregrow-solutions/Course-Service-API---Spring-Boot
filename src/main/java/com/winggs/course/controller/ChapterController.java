package com.winggs.course.controller;

import com.winggs.course.modal.dto.ChapterDto;
import com.winggs.course.modal.dto.ChapterSectionDetailDto;
import com.winggs.course.modal.dto.ChapterSectionDto;
import com.winggs.course.modal.dto.ChapterSectionListDto;
import com.winggs.course.modal.payload.CreateChapterPayload;
import com.winggs.course.security.AuthenticatedUser;
import com.winggs.course.security.CurrentUser;
import com.winggs.course.service.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Chapter APIs", description = "API for manage Chapter Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ChapterController {
    private final ChapterService chapterService;

    @Operation(summary = "Create new Chapter")
    @PreAuthorize("@eldtSecurity.hasUserEditPermission(#user)")
    @PostMapping("/admin/chapters")
    public ChapterDto create(@RequestBody CreateChapterPayload payload,
                             @CurrentUser AuthenticatedUser user) {
        return chapterService.create(payload);
    }

    @Operation(summary = "Get Details of Given Chapter")
    @GetMapping(value = {"/admin/chapters/{chapterId}", "/chapters/{chapterId}"})
    public Optional<ChapterDto> get(@PathVariable int chapterId) {
        return chapterService.get(chapterId);
    }

    @Operation(summary = "Get Details of Given Chapter Section")
    @GetMapping("/chapters/{indexNumber}/index")
    public Optional<ChapterSectionDto> getSectionDetailByIndex(@PathVariable String indexNumber) {
        return chapterService.getSectionByIndex(indexNumber);
    }

    @Operation(summary = "Get Details of Given Chapter Section")
    @GetMapping("/sections/{id}")
    public Optional<ChapterSectionDetailDto> getSectionDetailById(@PathVariable int id) {
        return chapterService.getSectionById(id);
    }

    //    @GetMapping(value = {"/chapters/{chapterId}/sections","/admin/chapters/{chapterId}/sections"})
//    public List<ChapterSectionDto> getAllChapterSection(@PathVariable int chapterId){
//        return chapterService.getAllSection(chapterId);
//    }
    @Deprecated
    @GetMapping(value = {"/chapters/{chapterId}/sections", "/admin/chapters/{chapterId}/sections"})
    public Page<ChapterSectionDto> getAllChapterSection(@PathVariable int chapterId,
                                                        @PageableDefault Pageable pageable) {
        return chapterService.getAllChapterSection(chapterId, pageable);
    }

    @Operation(summary = "Update Given Chapter Details")
    @PreAuthorize("@eldtSecurity.hasUserEditPermission(#user)")
    @PutMapping("/admin/chapters/{chapterId}")
    public Optional<ChapterDto> update(@PathVariable int chapterId,
                                       @RequestBody ChapterDto payload,
                                       @CurrentUser AuthenticatedUser user) {
        return chapterService.update(chapterId, payload);
    }

    @Operation(summary = "Create New Section under Given Chapter")
    @PreAuthorize("@eldtSecurity.hasUserEditPermission(#user)")
    @PostMapping("/admin/chapters/{chapterId}/sections")
    public ChapterSectionDto createSection(@PathVariable int chapterId,
                                           @RequestBody ChapterSectionDto sectionDto,
                                           @CurrentUser AuthenticatedUser user) {
        return chapterService.addSection(chapterId, sectionDto);
    }

    @Operation(summary = "Update Given Chapter Section Details")
    @PreAuthorize("@eldtSecurity.hasUserEditPermission(#user)")
    @PutMapping("/admin/chapters/{chapterId}/sections/{sectionId}")
    public Optional<ChapterSectionDto> updateSection(@PathVariable int chapterId,
                                                     @PathVariable int sectionId,
                                                     @RequestBody ChapterSectionDto sectionDto,
                                                     @CurrentUser AuthenticatedUser user) {
        return chapterService.updateSection(chapterId, sectionId, sectionDto);
    }

    @Operation(summary = "Get Details of  Given Chapter Section")
    @GetMapping(value = {"/admin/chapters/{chapterId}/sections/{sectionId}",
            "/chapters/{chapterId}/sections/{sectionId}"})
    public Optional<ChapterSectionDto> getSection(@PathVariable int chapterId,
                                                  @PathVariable int sectionId) {
        return chapterService.getSectionDetail(chapterId, sectionId);
    }

    @Operation(summary = "Get Section list of given chapter")
    @GetMapping("/chapters/{chapterId}/section-list")
    public List<ChapterSectionListDto> getAllSection(@PathVariable int chapterId) {
        return chapterService.getAllSection(chapterId);
    }

    @Operation(summary = "Delete Given Section Details")
    @DeleteMapping("/admin/sections/{sectionId}")
    public void deleteSection(@PathVariable int sectionId) {
        chapterService.deleteChapterSection(sectionId);
    }

}
