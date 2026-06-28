package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.RequestMapping;

@Controller("appTest")
public class TestController {


    @RequestMapping("/andrana")
    public String andrana() {
        System.out.println("AppTest: andrana()");
        return "mappings";
    }
    














        
    // @RequestMapping("/app/info")
    // public String info() {
    //     System.out.println("AppTest: info() appelée");
    //     return "mappings";
    // }
    
    // @RequestMapping("/app/test")
    // public String test() {
    //     System.out.println("AppTest: test() appelée");
    //     return "mappings";
    // }
}