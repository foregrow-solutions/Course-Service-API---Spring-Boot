package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.ChapterSection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChapterSectionListDto {
    int id;
    String index;
    String title;

    public static ChapterSectionListDto of(ChapterSection chapterSection) {
        ChapterSectionListDto output = new ChapterSectionListDto();
        output.setId(chapterSection.getId());
        output.setIndex(chapterSection.getIndexNumber());
        output.setTitle(chapterSection.getTitle());
        return output;
    }
}
