package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller("appTest")
public class TestController {
    
    @RequestMapping(value = "/app/hello", method = "GET")
    public String helloGet(HttpServletRequest request) {
        System.out.println("GET /app/hello appele");
        
        // Ajouter des attributs pour la JSP
        request.setAttribute("message", "GET /app/hello a ete appele");
        request.setAttribute("fomba", "GET");
        request.setAttribute("timestamp", new java.util.Date());
        request.setAttribute("status", "success");
        
        return "mappings";
    }
    
    @RequestMapping(value = "/app/hello", method = "POST")
    public String helloPost(HttpServletRequest request) {
        System.out.println("POST /app/hello appele");
        
        // Ajouter des attributs pour la JSP
        request.setAttribute("message", "POST /app/hello a ete appele");
        request.setAttribute("fomba", "POST");
        request.setAttribute("timestamp", new java.util.Date());
        request.setAttribute("status", "success");
        
        // Récupérer les paramètres POST
        String nom = request.getParameter("nom");
        if (nom != null) {
            request.setAttribute("nom", nom);
        }
        
        return "mappings";
    }
}