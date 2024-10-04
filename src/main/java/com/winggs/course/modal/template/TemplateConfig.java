package com.winggs.course.modal.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateConfig {

    @Bean
    public TemplateLocator getTemplateLocator() {
        return new TemplateLocator();
    }
}
