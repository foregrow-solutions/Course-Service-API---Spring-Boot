package com.winggs.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CourseApplication  extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(CourseApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CourseApplication.class, args);
	}

}
