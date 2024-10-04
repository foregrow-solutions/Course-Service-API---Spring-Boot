package com.winggs.course.modal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Optional;

@Builder
@Getter
@Setter
public class TemplateParam {
    String name;
    Type type;
    @Nullable
    String value;
    boolean required;

    public enum Type {
        Text, File, Number
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }
}
