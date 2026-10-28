package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.ModelView;
import mg.itu.framework.RequestMapping;
import mg.itu.framework.MySession;
import mg.itu.framework.Auth;
import mg.itu.framework.Role;

@Controller
public class SecureController {

    // SPRINT 10 : Annotation @Auth
    @Auth
    @RequestMapping(value = "/profile", method = "GET")
    public ModelView userProfile(MySession session) {
        // Le framework a déjà vérifié. On récupère juste pour l'affichage.
        String user = session.get("user", String.class);
        ModelView mv = new ModelView("profile");
        mv.addAttribute("user", user);
        return mv;
    }

}