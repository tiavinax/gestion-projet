// package mg.itu.framework;

// import mg.itu.framework.Controller;
// import mg.itu.framework.RequestMapping;
// import jakarta.servlet.http.HttpServletRequest;

// @Controller("Test")
// public class TestController {
    
//     @RequestMapping(value = "/test/info", method = "GET")
//     public String infoGet(HttpServletRequest request) {
//         System.out.println("GET /app/info appele");
        
//         // Ajouter des attributs pour la JSP
//         request.setAttribute("message", "GET /app/info a ete appele");
//         request.setAttribute("fomba", "GET");
//         request.setAttribute("timestamp", new java.util.Date());
//         request.setAttribute("status", "success");
        
//         return "mappings";
//     }
    
//     @RequestMapping(value = "/test/info", method = "POST")
//     public String infoPost(HttpServletRequest request) {
//         System.out.println("POST /app/info appele");
        
//         // Ajouter des attributs pour la JSP
//         request.setAttribute("message", "POST /app/info a ete appele");
//         request.setAttribute("fomba", "POST");
//         request.setAttribute("timestamp", new java.util.Date());
//         request.setAttribute("status", "success");
        
//         // Récupérer les paramètres POST
//         String nom = request.getParameter("nom");
//         if (nom != null) {
//             request.setAttribute("nom", nom);
//         }
        
//         return "mappings";
//     }
// }