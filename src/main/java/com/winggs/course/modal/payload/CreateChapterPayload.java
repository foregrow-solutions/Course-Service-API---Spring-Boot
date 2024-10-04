package com.winggs.course.modal.payload;

import com.winggs.course.modal.dto.ChapterSectionDto;
import lombok.Data;

@Data
public class CreateChapterPayload {
    String title;
    ChapterSectionDto sectionDto;
}
