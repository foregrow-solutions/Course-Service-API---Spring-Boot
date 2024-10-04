package com.winggs.course.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FacultyController {
    @RequestMapping(value = "/faculty-dashboard", method = RequestMethod.GET)
    public ModelAndView facultyDashboard() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("faculty/faculty-dashboard");
        return vi;
    }

    @RequestMapping(value = "/faculty-student", method = RequestMethod.GET)
    public ModelAndView facultyStudent() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("faculty/assign-student");
        return vi;
    }

    @RequestMapping(value = "/faculty-attendance", method = RequestMethod.GET)
    public ModelAndView facultyAttendance() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("faculty/mark-attendance");
        return vi;
    }


}
