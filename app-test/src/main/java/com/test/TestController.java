package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

import mg.itu.framework.ModelView;

@Controller("appTest")
public class TestController {

    @RequestMapping(value = "/model", method = "GET")
    public ModelView test(HttpServletRequest request) {
        ModelView mv = new ModelView("test");

        mv.addAttribute("Nombre 12", "12");
        mv.addAttribute("test", "Salut Mr Mbola !");

        return mv;
    }
}