package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.ModelView;
import mg.itu.framework.RequestMapping;
import mg.itu.framework.UrlMethod;
import mg.itu.framework.MethodInfo;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class SprintController {

    @RequestMapping(value = "/", method = "GET")
    public ModelView home() {
        ModelView mv = new ModelView("index");
        mv.addAttribute("sprintName", "Accueil");
        mv.addAttribute("serverTime", new Date());
        return mv;
    }

    @RequestMapping(value = "/sprint1", method = "GET")
    public ModelView sprint1(HttpServletRequest request) {
        ModelView mv = new ModelView("sprint1");

        // Récupérer les données depuis le contexte
        Map<UrlMethod, MethodInfo> urlMapping = (Map<UrlMethod, MethodInfo>) request.getServletContext()
                .getAttribute("urlMapping");
        List<Class<?>> controllers = (List<Class<?>>) request.getServletContext().getAttribute("controllers");

        mv.addAttribute("controllers", controllers);
        mv.addAttribute("totalControllers", controllers != null ? controllers.size() : 0);
        mv.addAttribute("totalUrls", urlMapping != null ? urlMapping.size() : 0);
        mv.addAttribute("serverTime", new Date());
        mv.addAttribute("sprintName", "SPRINT 1 - Scanner @Controller");
        mv.addAttribute("sprintDesc", "Liste des contrôleurs annotés avec @Controller");

        return mv;
    }

    @RequestMapping(value = "/sprint2", method = "GET")
    public ModelView sprint2(HttpServletRequest request) {
        ModelView mv = new ModelView("sprint2");

        Map<UrlMethod, MethodInfo> urlMapping = (Map<UrlMethod, MethodInfo>) request.getServletContext()
                .getAttribute("urlMapping");

        mv.addAttribute("urlMapping", urlMapping);
        mv.addAttribute("totalUrls", urlMapping != null ? urlMapping.size() : 0);
        mv.addAttribute("serverTime", new Date());
        mv.addAttribute("sprintName", "SPRINT 2 - @RequestMapping");
        mv.addAttribute("sprintDesc", "Mapping URL → Contrôleur → Méthode");

        return mv;
    }

    @RequestMapping(value = "/sprint3", method = "GET")
    public ModelView sprint3(HttpServletRequest request) {
        ModelView mv = new ModelView("sprint3");

        Map<UrlMethod, MethodInfo> urlMapping = (Map<UrlMethod, MethodInfo>) request.getServletContext()
                .getAttribute("urlMapping");

        mv.addAttribute("urlMapping", urlMapping);
        mv.addAttribute("totalUrls", urlMapping != null ? urlMapping.size() : 0);
        mv.addAttribute("serverTime", new Date());
        mv.addAttribute("sprintName", "SPRINT 3 - Méthodes HTTP (GET/POST)");
        mv.addAttribute("sprintDesc", "Mapping avec GET / POST sur la même URL");

        // Formulaire pour tester POST
        mv.addAttribute("showForm", true);

        return mv;
    }

    @RequestMapping(value = "/sprint3", method = "POST")
    public ModelView sprint3Post(HttpServletRequest request) {
        ModelView mv = new ModelView("sprint3");

        Map<UrlMethod, MethodInfo> urlMapping = (Map<UrlMethod, MethodInfo>) request.getServletContext()
                .getAttribute("urlMapping");

        String nom = request.getParameter("nom");
        String message = "POST reçu !";
        if (nom != null && !nom.isEmpty()) {
            message = "Bonjour " + nom + " ! POST reçu avec succès.";
        }

        mv.addAttribute("urlMapping", urlMapping);
        mv.addAttribute("totalUrls", urlMapping != null ? urlMapping.size() : 0);
        mv.addAttribute("serverTime", new Date());
        mv.addAttribute("sprintName", "SPRINT 3 - Méthodes HTTP (GET/POST)");
        mv.addAttribute("sprintDesc", "Mapping avec GET / POST sur la même URL");
        mv.addAttribute("showForm", true);
        mv.addAttribute("postMessage", message);
        mv.addAttribute("postSuccess", true);

        return mv;
    }

    @RequestMapping(value = "/sprint4", method = "GET")
    public ModelView sprint4(HttpServletRequest request) {
        ModelView mv = new ModelView("sprint4");

        Map<UrlMethod, MethodInfo> urlMapping = (Map<UrlMethod, MethodInfo>) request.getServletContext()
                .getAttribute("urlMapping");
        List<Class<?>> controllers = (List<Class<?>>) request.getServletContext().getAttribute("controllers");
        List<String> mappingErrors = (List<String>) request.getServletContext().getAttribute("mappingErrors");

        mv.addAttribute("controllers", controllers);
        mv.addAttribute("totalControllers", controllers != null ? controllers.size() : 0);
        mv.addAttribute("urlMapping", urlMapping);
        mv.addAttribute("totalUrls", urlMapping != null ? urlMapping.size() : 0);
        mv.addAttribute("mappingErrors", mappingErrors);
        mv.addAttribute("serverTime", new Date());
        mv.addAttribute("sprintName", "SPRINT 4 - ContextListener");
        mv.addAttribute("sprintDesc", "Initialisation du framework via ServletContextListener");

        return mv;
    }

    @RequestMapping(value = "/sprint5", method = "GET")
    public ModelView sprint5(HttpServletRequest request) {
        ModelView mv = new ModelView("sprint5");

        Map<UrlMethod, MethodInfo> urlMapping = (Map<UrlMethod, MethodInfo>) request.getServletContext()
                .getAttribute("urlMapping");
        List<Class<?>> controllers = (List<Class<?>>) request.getServletContext().getAttribute("controllers");

        mv.addAttribute("controllers", controllers);
        mv.addAttribute("totalControllers", controllers != null ? controllers.size() : 0);
        mv.addAttribute("urlMapping", urlMapping);
        mv.addAttribute("totalUrls", urlMapping != null ? urlMapping.size() : 0);
        mv.addAttribute("serverTime", new Date());
        mv.addAttribute("sprintName", "SPRINT 5 - ModelView");
        mv.addAttribute("sprintDesc", "Envoi de données vers une vue avec ModelView");
        mv.addAttribute("message", "Coucou Mr Mbola !");
        mv.addAttribute("version", "1.0");

        return mv;
    }

    
}