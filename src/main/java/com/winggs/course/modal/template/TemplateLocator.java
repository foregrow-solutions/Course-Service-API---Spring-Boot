package com.winggs.course.modal.template;

import com.winggs.course.modal.TemplateParam;
import com.winggs.course.modal.constants.TemplateConstants;
import lombok.Getter;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public class TemplateLocator {
    public File getFile(TemplateConfig templateConfig) {
        return new File(Objects.requireNonNull(getClass().getResource(templateConfig.fileName)).getFile());
    }
    @Getter
    public enum TemplateConfig {
        CERTIFICATE_3("ct001", "/static/templates/certificate.html", TemplateConstants.ParamSet.BT1);

        final String id;
        final String fileName;
        final Map<String, TemplateParam> params;
        TemplateConfig(String id, String fileName, Map<String, TemplateParam> params) {
            this.id = id;
            this.fileName = fileName;
            this.params = params;
        }

        public static TemplateConfig value(String id) {
            for (TemplateConfig templateConfig : TemplateConfig.values())
                if (templateConfig.id.equals(id)) return templateConfig;
            throw new IllegalArgumentException();
        }
    }
}
