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
    
    // Formulaire d'ajout
    @RequestMapping(value = "/user/add", method = "GET")
    public ModelView addForm() {
        return new ModelView("sprint7_form");
    }
    
    // Sauvegarde avec binding automatique
    @RequestMapping(value = "/user/save", method = "POST")
    public ModelView save(String nom, String email, int age) {
        System.out.println("Binding automatique :");
        System.out.println("  nom   = " + nom);
        System.out.println("  email = " + email);
        System.out.println("  age   = " + age);
        
        // Créer et sauvegarder l'utilisateur
        User user = new User();
        user.setId(userService.getAllUsers().size() + 1);
        user.setName(nom);
        user.setEmail(email);
        userService.save(user);
        
        ModelView mv = new ModelView("user_list");
        mv.addAttribute("users", userService.getAllUsers());
        mv.addAttribute("message", "Utilisateur " + nom + " ajouté avec succès !");
        mv.addAttribute("serverTime", new Date());
        return mv;
    }
    
    // Liste des utilisateurs
    @RequestMapping(value = "/user/list", method = "GET")
    public ModelView list() {
        ModelView mv = new ModelView("sprint7_list");
        mv.addAttribute("users", userService.getAllUsers());
        mv.addAttribute("serverTime", new Date());
        return mv;
    }

}