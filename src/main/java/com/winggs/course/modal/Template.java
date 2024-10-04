package com.winggs.course.modal;

import com.winggs.course.modal.common.exception.DataConstraintViolationException;
import com.winggs.course.modal.converter.HtmlToPdfConverter;
import com.winggs.course.modal.template.TemplateLocator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class Template {
    String id;
    TemplateLocator.TemplateConfig config;
    private final TemplateLocator templateLocator = new TemplateLocator();
    private String format;
    private final HtmlToPdfConverter htmlToPdfConverter = new HtmlToPdfConverter();

    public Template(String id) {
        this.id = id;

        this.config = TemplateLocator.TemplateConfig.value(id);
        File templateFile = templateLocator.getFile(config);
        try {
            format = Files.readString(templateFile.toPath());
        } catch (IOException iox) {
            log.error("Failed to read template {}", config.name(), iox);
        }
    }

    private List<String> validate(Map<String, String> data) {
        List<String> absentFields = new ArrayList<>();
        for (var templateParam : config.getParams().values().stream().filter(TemplateParam::isRequired).toList()) {
            if (!data.containsKey(templateParam.name) || data.getOrDefault(templateParam.name, "").isBlank())
                absentFields.add(templateParam.name);
        }
        return absentFields;
    }

    private Set<TemplateParam> mapToData(Map<String, String> data) {
        return config.getParams().values().stream().map(templateParam-> {
            if (!data.getOrDefault(templateParam.name, "").isBlank()) {
                templateParam.setValue(data.get(templateParam.name));
                return templateParam;
            } else
                return null;
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public File generatePdf(Map<String, String> data, String destinationFilePath) throws IOException {
        var absentFields = validate(data);
        if (absentFields.isEmpty()) {
            Document document = Jsoup.parse(format, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.html);

            Set<TemplateParam> dataSet = mapToData(data);
            dataSet.forEach(param-> {
                var element = document.getElementById(param.getName());
                if (element != null) {
                    element.text(param.getValue().orElse(""));
                }
            });
            Document parsedDocument = Jsoup.parse(document.html(), "UTF-8");
            parsedDocument.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            return htmlToPdfConverter.convert(parsedDocument, destinationFilePath);
        } else {
            throw new DataConstraintViolationException("Following params are not present or value not provided for: "+ String.join(", ", absentFields)+".");
        }
    }
}
