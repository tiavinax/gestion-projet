package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.ModelView;
import mg.itu.framework.RequestMapping;
import mg.itu.framework.Autowired;
import com.test.service.UserService;
import com.test.model.User;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // ============================================
    // SPRINT 7 : Binding par attributs
    // ============================================
    
    @RequestMapping(value = "/user/sprint7", method = "GET")
    public ModelView addForm() {
        return new ModelView("sprint7_form");
    }
    
    @RequestMapping(value = "/user/save-attr", method = "POST")
    public ModelView saveAttr(String nom, String email, int age) {
        System.out.println("=== Sprint 7 : Binding attributs ===");
        System.out.println("  nom   = " + nom);
        System.out.println("  email = " + email);
        System.out.println("  age   = " + age);
        
        User user = new User();
        user.setId(userService.getAllUsers().size() + 1);
        user.setName(nom);
        user.setEmail(email);
        userService.save(user);
        
        ModelView mv = new ModelView("sprint7_list");
        mv.addAttribute("users", userService.getAllUsers());
        mv.addAttribute("message", "[Sprint 7] Utilisateur " + nom + " ajouté (binding attributs)");
        mv.addAttribute("serverTime", new Date());
        return mv;
    }
    
    
    // ============================================
    // SPRINT 7b : Binding Object
    // ============================================
    
    @RequestMapping(value = "/user/sprint7b", method = "GET")
    public ModelView addFormObject() {
        return new ModelView("sprint7b_form");
    }
    
    @RequestMapping(value = "/user/save-object", method = "POST")
    public ModelView saveObject(User user) {
        System.out.println("=== Sprint 7b : Binding Object ===");
        System.out.println("  user.name  = " + user.getName());
        System.out.println("  user.email = " + user.getEmail());
        
        user.setId(userService.getAllUsers().size() + 1);
        userService.save(user);
        
        ModelView mv = new ModelView("sprint7_list");
        mv.addAttribute("users", userService.getAllUsers());
        mv.addAttribute("message", "[Sprint 7b] Utilisateur " + user.getName() + " ajouté (binding object)");
        mv.addAttribute("serverTime", new Date());
        return mv;
    }

    @RequestMapping(value = "/user/list", method = "GET")
    public ModelView list() {
        ModelView mv = new ModelView("sprint7_list");
        mv.addAttribute("users", userService.getAllUsers());
        mv.addAttribute("serverTime", new Date());
        return mv;
    }
}