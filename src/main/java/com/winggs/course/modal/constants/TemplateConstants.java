package com.winggs.course.modal.constants;


import com.winggs.course.modal.TemplateParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TemplateConstants {
    public static class ParamSet {
        public static final Map<String, TemplateParam> BT1 = new HashSet<>(Arrays.asList(
                TemplateParam.builder().name("student_name").type(TemplateParam.Type.Text).required(true).build(),
                TemplateParam.builder().name("student_score").type(TemplateParam.Type.Text).build(),
                TemplateParam.builder().name("completion_date").type(TemplateParam.Type.Text).build()

        )).stream().collect(Collectors.toMap(TemplateParam::getName, Function.identity()));
    }
}
