package com.winggs.course.modal.dto;

import com.winggs.course.modal.entity.Chapter;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ChapterDto {
    int id;
    String title;
    String subTitle;
    List<ChapterSectionDto> sections;

    public static ChapterDto of(Chapter chapter) {
        ChapterDto output = new ChapterDto();
        output.setId(chapter.getId());
        output.setTitle(chapter.getTitle());
        return output;
    }
}
