package com.winggs.course.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("/home/tomcat/webapps/eldtinc_new/uploads/");
//                .addResourceLocations("file:D:/Java-Pankaj/dev/eldt-inc_new/uploads/");
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }


//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setPrefix("classpath:/templates/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers("/resources/**");
//    }


}
