package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

import mg.itu.framework.ModelView;

@Controller("appTest")
public class TestController {
    
    @RequestMapping(value = "/modelView", method = "GET")
    public ModelView test() {
        ModelView mv = new ModelView("modelView.jsp");
        String message = "Hello from TestController!";
        mv.addAttribute("message", message);
        return mv;
    }
}