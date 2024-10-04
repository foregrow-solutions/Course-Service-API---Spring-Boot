package com.winggs.course.service;

import com.winggs.course.modal.dto.SchoolDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface SchoolService {
    SchoolDto add(SchoolDto schoolDto);

    Optional<SchoolDto> update(String id, SchoolDto schoolDto);

    Optional<SchoolDto> get(String id);

    ResponseEntity<?> getCount(String id);

    ResponseEntity<?> getAdminCount();

    List<SchoolDto> getAll();


}
