package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.RequestMapping;
import mg.itu.framework.WebAPI;
import mg.itu.framework.Autowired;
import mg.itu.framework.ModelView;
import com.test.service.UserService;
import com.test.model.User;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ApiController {
    
    @Autowired
    private UserService userService;
    
    // ============================================
    // API JSON
    // ============================================
    
    @WebAPI
    @RequestMapping(value = "/api/users", method = "GET")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    
    @WebAPI
    @RequestMapping(value = "/api/user", method = "GET")
    public User getUser(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        int id = idParam != null ? Integer.parseInt(idParam) : 1;
        return userService.getUserById(id);
    }
    
    @WebAPI
    @RequestMapping(value = "/api/hello", method = "GET")
    public Map<String, Object> hello() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("message", "Hello from API");
        data.put("status", "success");
        data.put("timestamp", new Date().toString());
        return data;
    }
    
    @WebAPI
    @RequestMapping(value = "/api/status", method = "GET")
    public Map<String, Object> status() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("framework", "MVC Artisanal");
        data.put("version", "1.0");
        data.put("sprint", "6 - WebAPI");
        data.put("operational", true);
        return data;
    }
    
    // ============================================
    // Page de documentation (VUE)
    // ============================================
    
    @RequestMapping(value = "/sprint6", method = "GET")
    public ModelView sprint6(HttpServletRequest request) {
        ModelView mv = new ModelView("sprint6");
        mv.addAttribute("serverTime", new Date());
        mv.addAttribute("sprintName", "SPRINT 6 - Web Service (JSON)");
        mv.addAttribute("sprintDesc", "Retour de JSON via l'annotation @WebAPI");
        return mv;
    }

    // Formulaire
    @RequestMapping(value = "/sprint7", method = "GET")
    public ModelView sprint7(HttpServletRequest request) {
        ModelView mv = new ModelView("sprint7_form");
        mv.addAttribute("serverTime", new Date());
        return mv;
    }

    @RequestMapping(value = "/sprint7b", method = "GET")
    public ModelView sprint7b(HttpServletRequest request) {
        ModelView mv = new ModelView("sprint7b_form");
        mv.addAttribute("serverTime", new Date());
        return mv;
    }
}