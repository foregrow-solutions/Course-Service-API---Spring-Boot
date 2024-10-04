package com.winggs.course.controller;

import com.winggs.course.modal.dto.AttendanceDto;
import com.winggs.course.modal.dto.SchoolFacilityDto;
import com.winggs.course.modal.dto.StudentDto;
import com.winggs.course.modal.enums.FacilityDocType;
import com.winggs.course.modal.payload.StudentAssignedPayload;
import com.winggs.course.security.AuthenticatedUser;
import com.winggs.course.security.CurrentUser;
import com.winggs.course.service.FacilityService;
import com.winggs.course.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "School Facility APIs", description = "API for manage School Facility Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class SchoolFacilityController {

    private final UserService userService;
    private final FacilityService facilityService;

    @Operation(summary = "Get List of Given School Facility")
    @GetMapping("/admin/schools/{schoolId}/facilities")
    public Page<SchoolFacilityDto> getSchoolFacilityList(@PathVariable String schoolId,
                                                         @PageableDefault Pageable pageable) {
        return facilityService.getAllFacility(schoolId, pageable);
    }

    @Operation(summary = "Get List of all Assigned Student list")
    @GetMapping("/assigned/students")
    public List<StudentDto> getAllAssignedStudent(@CurrentUser AuthenticatedUser user) {
        int id = facilityService.getByUsername(user.getUsername()).getId();
        return facilityService.getAllStudent(id);
    }

    @Operation(summary = "Get Count Of All Assigned Students")
    @GetMapping("/students/count")
    public ResponseEntity<?> getAssignedStudentCount(@CurrentUser AuthenticatedUser user) {
        int id = facilityService.getByUsername(user.getUsername()).getId();
        return facilityService.getCount(id);
    }

    @Operation(summary = "Assigned Facility Member for Students")
    @PostMapping("/admin/facility/{id}/assigned")
    public void addFacilityMember(@PathVariable int id,
                                  @RequestBody StudentAssignedPayload payload) {
        facilityService.assign(id, payload);
    }

    @Operation(summary = "Add Facility Member in Given School")
    @PostMapping("/schools/{schoolId}/facility")
    public SchoolFacilityDto addFacilityMember(@PathVariable String schoolId,
                                               @RequestBody SchoolFacilityDto schoolFacilityDto) {
        return facilityService.addFacility(schoolId, schoolFacilityDto);
    }

    @Operation(summary = "Update Given Facility Member Details")
    @PutMapping("/admin/faculty/{id}")
    public Optional<SchoolFacilityDto> updateFacilityMember(@PathVariable int id,
                                                            @RequestBody SchoolFacilityDto schoolFacilityDto) {
        return facilityService.update(id, schoolFacilityDto);
    }

    @Operation(summary = "Get Given Facility Member Details")
    @GetMapping("/faculty/{id}")
    public Optional<SchoolFacilityDto> getFacilityMember(@PathVariable int id) {
        return facilityService.get(id);
    }

    @Operation(summary = "Upload Doc for Given Faculty Member")
    @PostMapping("/faculty/{id}/upload")
    public String upload(@PathVariable int id,
                         @RequestParam FacilityDocType type,
                         @RequestBody MultipartFile file) throws IOException {
        return facilityService.uploadDoc(id, type, file);
    }

    @Operation(summary = "Mark Student Attendance")
    @PostMapping("/faculty/mark-attendance")
    public AttendanceDto mark(@RequestBody AttendanceDto attendanceDto,
                              @CurrentUser AuthenticatedUser authenticatedUser) {
        return facilityService.markAttendance(authenticatedUser.getUsername(), attendanceDto);
    }

    @Operation(summary = "Get All Facility List given School")
    @GetMapping("/admin/schools/{schoolId}/facility-list")
    public List<SchoolFacilityDto> getAllStudentList(@PathVariable String schoolId) {
        return facilityService.getAllFacilityList(schoolId);
    }
}
