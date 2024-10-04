package com.winggs.course.controller;

import com.winggs.course.modal.dto.StudentDto;
import com.winggs.course.modal.dto.StudentResultDto;
import com.winggs.course.security.AuthenticatedUser;
import com.winggs.course.security.CurrentUser;
import com.winggs.course.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Tag(name = "Student APIs", description = "API for manage Student Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @RequestMapping(value = "/student-dashboard", method = RequestMethod.GET)
    public ModelAndView studentDashboard() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("student/student-dashboard");
        return vi;
    }

    @RequestMapping(value = "/student-class", method = RequestMethod.GET)
    public ModelAndView studentClassView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("student/chapter");
        return vi;
    }

    @RequestMapping(value = "/student-class-test", method = RequestMethod.GET)
    public ModelAndView studentClassTestView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("student/echo-chapter");
        return vi;
    }

    @RequestMapping(value = "/student-test", method = RequestMethod.GET)
    public ModelAndView studentTestView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("student/quiz");
        return vi;
    }

    @RequestMapping(value = "/student-result", method = RequestMethod.GET)
    public ModelAndView studentResultView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("student/result");
        return vi;
    }

    @RequestMapping(value = "/student-certificate", method = RequestMethod.GET)
    public ModelAndView studentCertificateView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("student/certificate");
        return vi;
    }

    @Operation(summary = "Register Student ")
    @PostMapping("/students")
    public StudentDto addStudent(@RequestBody StudentDto studentDto) {
        return studentService.addStudent("9d270d10-dc15-416c-b1e1-27357a2f26a6", studentDto);
    }

    @Operation(summary = "Add Student in Given School")
    @PostMapping("/schools/{schoolId}/students")
    public StudentDto addStudentGivenSchool(@PathVariable String schoolId,
                                            @RequestBody StudentDto studentDto) {
        return studentService.addStudent(schoolId, studentDto);
    }

    @Operation(summary = "Add Student in Given School")
    @PutMapping("/schools/{schoolId}/students/{studentId}")
    public Optional<StudentDto> updateStudentDetails(@PathVariable String schoolId,
                                                     @PathVariable String studentId,
                                                     @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(schoolId, studentId, studentDto);
    }

    @Operation(summary = "Get All Students for Given School")
    @GetMapping(value = {"/students", "/admin/schools/{schoolId}/students"})
    public Page<StudentDto> getAllStudentGivenSchool(@PathVariable String schoolId) {
        return studentService.getAll(schoolId, Pageable.unpaged());
    }

    @Operation(summary = "Get All Students")
    @GetMapping("/admin/students")
    public Page<StudentDto> getAllStudent(@PageableDefault Pageable pageable) {
        return studentService.getAllStudent(pageable);
    }

    @Operation(summary = "Get Details of Given Student")
    @GetMapping("/students/{studentId}")
    public Optional<StudentDto> getAllStudent(@PathVariable String studentId) {
        return studentService.get(studentId);
    }

    @Operation(summary = "Get All Students")
    @GetMapping("/admin/schools/{schoolId}/student-list")
    public List<StudentDto> getAllStudentList(@PathVariable String schoolId) {
        return studentService.getSchoolStudentList(schoolId);
    }

    @Operation(summary = "Get Logged in Student Results")
    @GetMapping("/result")
    public List<StudentResultDto> getAllStudentList(@CurrentUser AuthenticatedUser authenticatedUser) {
        return studentService.getAllStudentScore(authenticatedUser.getUsername());
    }

    @Operation(summary = "Get Given Result Details")
    @GetMapping("/results/{id}")
    public Optional<StudentResultDto> getResult(@PathVariable int id,
                                                @CurrentUser AuthenticatedUser authenticatedUser) {
        return studentService.getStudentScore(id);
    }

    @Operation(summary = "Submit Quiz")
    @PostMapping("/quiz/submit")
    public StudentResultDto saveQuiz(@RequestBody List<Integer> answerIds,
                                     @CurrentUser AuthenticatedUser user) {
        return studentService.submitQuiz(user.getUsername(), answerIds);
    }
}
