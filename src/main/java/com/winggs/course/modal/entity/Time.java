package com.winggs.course.modal.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Embeddable;
import java.text.MessageFormat;
import java.time.Instant;

@Data
@Embeddable
public class Time {
    private Instant in;
    private Instant out;

    @JsonProperty("summary")
    public String summary() {
        return MessageFormat.format("{0} to {1} hours", in, out);
    }
}
