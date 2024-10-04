package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.ChapterSection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChapterSectionDetailDto {
    int id;
    int chapterId;
    int next;
    int previous;
    String indexNumber;
    String title;
    String description;
    String descriptionContext;

    public static ChapterSectionDetailDto of(ChapterSection section, int next, int previous) {
        ChapterSectionDetailDto output = new ChapterSectionDetailDto();
        output.setId(section.getId());
        output.setChapterId(section.getChapter().getId());
        output.setIndexNumber(section.getIndexNumber());
        output.setTitle(section.getTitle());
        output.setDescription(section.getDescription());
        output.setDescriptionContext(section.getDescriptionContext());
        output.setNext(next);
        output.setPrevious(previous);
        return output;
    }
}
