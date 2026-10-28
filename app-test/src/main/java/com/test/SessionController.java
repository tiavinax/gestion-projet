package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.ModelView;
import mg.itu.framework.RequestMapping;
import mg.itu.framework.MySession;

@Controller
public class SessionController {

    // Méthode commune pour préparer les données de la vue
    private ModelView buildSessionView(MySession session) {
        ModelView mv = new ModelView("sprint9_view");

        // Compteur
        Integer compteur = session.get("compteur", Integer.class);
        compteur = (compteur == null) ? 1 : compteur + 1;
        session.set("compteur", compteur);

        // Utilisateur
        String username = session.get("username", String.class);

        mv.addAttribute("compteur", compteur);
        mv.addAttribute("sessionId", session.getId());
        mv.addAttribute("username", username);
        
        return mv;
    }

    // 1. Affichage normal
    @RequestMapping(value = "/sprint9", method = "GET")
    public ModelView testSession(MySession session) {
        return buildSessionView(session);
    }

    // 2. Action du formulaire de connexion
    @RequestMapping(value = "/sprint9/login", method = "POST")
    public ModelView login(String nom, MySession session) {
        // On stocke le nom dans la session
        session.set("username", nom);
        
        // On renvoie directement la vue (pas de redirection)
        return buildSessionView(session);
    }
    
    // 3. Déconnexion
@RequestMapping(value = "/sprint9/logout", method = "GET")
public ModelView logout(MySession session) {
    session.invalidate(); // Détruit la session
    
    // On renvoie une vue VIERGE (sans appeler buildSessionView)
    ModelView mv = new ModelView("sprint9_view");
    mv.addAttribute("compteur", 0);
    mv.addAttribute("sessionId", "Session détruite");
    mv.addAttribute("username", null); 
    return mv;
}
}