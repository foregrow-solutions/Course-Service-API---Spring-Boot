package com.winggs.course.service;

import com.winggs.course.modal.dto.AttendanceDto;
import com.winggs.course.modal.dto.SchoolFacilityDto;
import com.winggs.course.modal.dto.StudentDto;
import com.winggs.course.modal.entity.Facility;
import com.winggs.course.modal.enums.FacilityDocType;
import com.winggs.course.modal.payload.StudentAssignedPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FacilityService {
    SchoolFacilityDto addFacility(String schoolId, SchoolFacilityDto schoolFacilityDto);

    Optional<SchoolFacilityDto> get(int id);
    Optional<SchoolFacilityDto> update(int id, SchoolFacilityDto schoolFacilityDto);

    String uploadDoc(int id, FacilityDocType type, MultipartFile file) throws IOException;


    Page<SchoolFacilityDto> getAllFacility(String schoolId, Pageable pageable);

    void assignedStudent(int facilityId, StudentAssignedPayload assignedPayload);

    List<StudentDto> getAllStudent(int user);

    ResponseEntity<?> getCount(int id);

    Facility getByUsername(String user);

    void assign(int facilityId, StudentAssignedPayload assignedPayload);

    List<SchoolFacilityDto> getAllFacilityList(String schoolId);


    AttendanceDto markAttendance(String facilityId, AttendanceDto attendanceDto);
}
