<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sprint 5b - Repository + Container</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f7fa; padding: 40px; }
        .container { max-width: 900px; margin: 0 auto; background: white; border-radius: 12px; padding: 40px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        h1 { color: #2c3e50; border-bottom: 3px solid #9b59b6; padding-bottom: 15px; margin-bottom: 20px; }
        .badge { display: inline-block; background: #9b59b6; color: white; padding: 4px 14px; border-radius: 20px; font-size: 14px; font-weight: 600; }
        .info-bar { background: #ecf0f1; padding: 15px 20px; border-radius: 8px; margin-bottom: 25px; display: flex; justify-content: space-between; flex-wrap: wrap; gap: 10px; }
        .card { background: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 20px; border-left: 4px solid #9b59b6; }
        .card h3 { color: #2c3e50; margin-bottom: 10px; }
        .card ul { list-style: none; padding-left: 0; }
        .card li { padding: 6px 0; border-bottom: 1px solid #ecf0f1; font-family: monospace; }
        .card li:last-child { border-bottom: none; }
        .btn-back { display: inline-block; background: #3498db; color: white; padding: 10px 25px; border-radius: 6px; text-decoration: none; margin-top: 20px; }
        .btn-back:hover { background: #2980b9; }
        .footer { margin-top: 30px; padding-top: 20px; border-top: 1px solid #ecf0f1; text-align: center; color: #95a5a6; font-size: 13px; }
        .bean-item { background: white; padding: 8px 12px; border-radius: 4px; margin: 4px 0; }
        .bean-class { color: #9b59b6; font-weight: 600; }
        .bean-instance { color: #2c3e50; font-family: monospace; font-size: 13px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>SPRINT 5b - Repository + Spring Container</h1>
        
        <!-- Liste des utilisateurs -->
        <div class="card">
            <h3>Utilisateurs</h3>
            <ul>
                <%
                    List<String> users = (List<String>) request.getAttribute("users");
                    if (users != null) {
                        for (String user : users) {
                %>
                    <li>• <%= user %></li>
                <%
                        }
                    }
                %>
            </ul>
        </div>
        
        <!-- Liste des Beans -->
        <!-- <div class="card">
            <h3>Beans du conteneur ApplicationContext</h3>
            <%
                Map<Class<?>, Object> beans = (Map<Class<?>, Object>) request.getAttribute("beans");
                if (beans != null) {
                    int i = 1;
                    for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
            %>
                <div class="bean-item">
                    <span class="bean-class"><%= i++ %>. <%= entry.getKey().getSimpleName() %></span>
                    <span style="color: #95a5a6;">→</span>
                    <span class="bean-instance"><%= entry.getValue().getClass().getName() %></span>
                </div>
            <%
                    }
                }
            %>
        </div>
        
        <div style="background: #e8f4fd; padding: 15px; border-radius: 8px; margin: 20px 0;">
            <h4 style="color: #2c3e50;">🔍 Injection de dépendances</h4>
            <p style="font-size: 14px; color: #555; margin-top: 8px;">
                UserService → <strong>@Autowired</strong> UserRepository
            </p>
            <p style="font-size: 14px; color: #555;">
                SprintController → <strong>@Autowired</strong> UserService
            </p>
        </div>
         -->
        <a href="/app-test/" class="btn-back">Retour à l'accueil</a>
        <div class="footer"><strong>Framework MVC Artisanal</strong> &bull; Sprint 5b - Spring Container</div>
    </div>
</body>
</html>