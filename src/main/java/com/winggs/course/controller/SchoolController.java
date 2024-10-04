package com.winggs.course.controller;

import com.winggs.course.modal.dto.SchoolDto;
import com.winggs.course.security.AuthenticatedUser;
import com.winggs.course.security.CurrentUser;
import com.winggs.course.service.FacilityService;
import com.winggs.course.service.SchoolService;
import com.winggs.course.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Tag(name = "School APIs", description = "API for manage School Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService service;
    private final FacilityService facilityService;
    private final UserService userService;

    @RequestMapping(value = "/school-dashboard", method = RequestMethod.GET)
    public ModelAndView schoolDashboard() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("school/school-dashboard");
        return vi;
    }

    @RequestMapping(value = "/add-student", method = RequestMethod.GET)
    public ModelAndView addStudent() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("school/student/add-student");
        return vi;
    }

    @RequestMapping(value = "/edit-student", method = RequestMethod.GET)
    public ModelAndView editStudent() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("school/student/edit-student");
        return vi;
    }

    @RequestMapping(value = "/school-students", method = RequestMethod.GET)
    public ModelAndView getSchoolStudents() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("school/student/all-student");
        return vi;
    }

    @RequestMapping(value = "add-faculty", method = RequestMethod.GET)
    public ModelAndView addFaculty() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("school/faculty/add-faculty");
        return vi;
    }

    @RequestMapping(value = "/faculty-list", method = RequestMethod.GET)
    public ModelAndView getSchoolFaculty() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("school/faculty/all-faculty");
        return vi;
    }

    @RequestMapping(value = "/assign-student", method = RequestMethod.GET)
    public ModelAndView assignStudent() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("school/faculty/assign-student");
        return vi;
    }

    @RequestMapping(value = "/student-attendance", method = RequestMethod.GET)
    public ModelAndView getStudentAttendance() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("school/student-attendance");
        return vi;
    }


    @Operation(summary = "Add new School")
    @PreAuthorize("@eldtSecurity.hasUserEditPermission(#user)")
    @PostMapping("/admin/schools")
    public SchoolDto create(@RequestBody SchoolDto schoolDto,
                            @CurrentUser AuthenticatedUser user) {
        return service.add(schoolDto);
    }

    @Operation(summary = "Update School details")
    @PutMapping(value = {"/admin/schools/{id}", "/schools/{id}"})
    public Optional<SchoolDto> update(@PathVariable String id,
                                      @RequestBody SchoolDto schoolDto) {
        return service.update(id, schoolDto);
    }

    @Operation(summary = "Get Details of Given Schools")
    @GetMapping(value = {"/admin/schools/{id}", "/schools/{id}"})
    public Optional<SchoolDto> get(@PathVariable String id) {
        return service.get(id);
    }

    @Operation(summary = "Get Details of Given Schools")
    @GetMapping("/schools/{id}/count")
    public ResponseEntity<?> getCount(@PathVariable String id) {
        return service.getCount(id);
    }

    @Operation(summary = "Get Total Counts for Admin Dashboard")
    @GetMapping("/admin/count")
    public ResponseEntity<?> getCount() {
        return service.getAdminCount();
    }

    @Operation(summary = "Get List of All Schools")
    @GetMapping("/admin/schools")
    public List<SchoolDto> getSchoolList() {
        return service.getAll();
    }


}
