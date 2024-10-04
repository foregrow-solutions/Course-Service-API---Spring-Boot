package com.winggs.course.modal.dto;

import com.winggs.course.modal.enums.ResultStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {
    int id;

    ResultStatus status;

}
