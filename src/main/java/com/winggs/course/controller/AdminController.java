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
public class AdminController {

    @RequestMapping(value = "/admin-dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("admin/admin-dashboard");
        return vi;
    }

    @RequestMapping(value = "/create-chapter", method = RequestMethod.GET)
    public ModelAndView createChapterView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("admin/chapter/create-chapter");
        return vi;
    }

    @RequestMapping(value = "/edit-chapter", method = RequestMethod.GET)
    public ModelAndView editChapterView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("admin/chapter/edit-chapter");
        return vi;
    }

    @RequestMapping(value = "/chapter-list", method = RequestMethod.GET)
    public ModelAndView chapterListView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("admin/chapter/chapter-list");
        return vi;
    }

    @RequestMapping(value = "/add-school", method = RequestMethod.GET)
    public ModelAndView addSchoolForm() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("admin/school/add-school");
        return vi;
    }

    @RequestMapping(value = "/edit-school", method = RequestMethod.GET)
    public ModelAndView editSchoolForm() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("admin/school/edit-school");
        return vi;
    }

    @RequestMapping(value = "/student-list", method = RequestMethod.GET)
    public ModelAndView studentListView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("admin/student/student-list");
        return vi;
    }

    @RequestMapping(value = "/create-quiz", method = RequestMethod.GET)
    public ModelAndView createQuizView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("admin/quiz/add-question");
        return vi;
    }
}
