package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.ModelView;
import mg.itu.framework.RequestMapping;
import mg.itu.framework.MySession;

@Controller
public class AuthController {

    @RequestMapping(value = "/auth/login", method = "GET")
    public ModelView loginForm() {
        return new ModelView("login");
    }

    @RequestMapping(value = "/auth/do-login", method = "POST")
    public ModelView doLogin(String username, MySession session) {
        if (username != null && !username.trim().isEmpty()) {
            
            session.set("user", username);
            
            // Attribution du rôle
            if ("admin".equalsIgnoreCase(username)) {
                session.set("role", "admin");
            } else {
                session.set("role", "user");
            }
            
            ModelView mv = new ModelView("profile");
            mv.addAttribute("user", username);
            return mv;
        }
        
        ModelView mv = new ModelView("login");
        mv.addAttribute("error", "Le nom d'utilisateur est obligatoire.");
        return mv;
    }

    @RequestMapping(value = "/auth/logout", method = "GET")
    public ModelView logout(MySession session) {
        session.invalidate();
        return new ModelView("login");
    }
}