package com.winggs.course.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("index");
        return vi;
    }

    @RequestMapping(value = "/about-us", method = RequestMethod.GET)
    public ModelAndView aboutUs() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("view/about-us");
        return vi;
    }

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public ModelAndView blogView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("view/blog");
        return vi;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contactView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("view/contact");
        return vi;
    }

    @RequestMapping(value = "/eldt-course", method = RequestMethod.GET)
    public ModelAndView courseView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("view/eldt-course");
        return vi;
    }

    @RequestMapping(value = "/eldt-prep-app", method = RequestMethod.GET)
    public ModelAndView prepAppView() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("view/eldt-prep-app");
        return vi;
    }

}
