package com.test;  

import mg.itu.framework.Controller;

@Controller("appTest")
public class TestController {
    
    public String execute() {
        return "app_test_view";
    }
    
    public String getInfo() {
        return "Contrôleur de l'application de test";
    }
}