package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.ModelView;
import mg.itu.framework.RequestMapping;
import mg.itu.framework.MySession;
import java.util.*;

@Controller
public class SessionController {

    @RequestMapping(value = "/sprint9", method = "GET")
    public ModelView testSession(MySession session) {
        ModelView mv = new ModelView("sprint9_view");

        // 1. Récupérer un compteur depuis la session (ou initialiser à 0)
        Integer compteur = session.get("compteur", Integer.class);
        if (compteur == null) {
            compteur = 1;
        } else {
            compteur++;
        }
        
        // 2. Sauvegarder dans la session
        session.set("compteur", compteur);

        // 3. Envoyer à la vue
        mv.addAttribute("compteur", compteur);
        mv.addAttribute("sessionId", session.getId());
        
        return mv;
    }
    
    @RequestMapping(value = "/sprint9/reset", method = "GET")
    public ModelView resetSession(MySession session) {
        session.invalidate(); // Détruit la session
        return new ModelView("sprint9_view");
    }
}