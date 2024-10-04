package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.ChapterSection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChapterSectionDto {
    int id;
    int chapterId;
    String indexNumber;
    String title;
    String description;
    String descriptionContext;

    public static ChapterSectionDto of(ChapterSection chapterSection) {
        ChapterSectionDto output = new ChapterSectionDto();
        output.setId(chapterSection.getId());
        output.setChapterId(chapterSection.getChapter().getId());
        output.setIndexNumber(chapterSection.getIndexNumber());
        output.setTitle(chapterSection.getTitle());
        output.setDescription(chapterSection.getDescription());
        output.setDescriptionContext(chapterSection.getDescriptionContext());
        return output;
    }
}
