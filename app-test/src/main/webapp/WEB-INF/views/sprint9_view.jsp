<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Sprint 9 - Session</title>
    <style>
        body { font-family: Arial; padding: 40px; background: #f5f7fa; text-align: center; }
        .container { max-width: 500px; margin: 0 auto; background: white; padding: 40px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }
        h1 { color: #8e44ad; }
        .box { background: #f4ecf7; border-left: 4px solid #8e44ad; padding: 20px; margin: 20px 0; text-align: left; }
        input[type="text"] { padding: 8px; border-radius: 4px; border: 1px solid #ccc; width: 60%; }
        .btn { display: inline-block; padding: 10px 20px; background: #8e44ad; color: white; text-decoration: none; border-radius: 6px; border: none; cursor: pointer; }
        .btn-red { background: #e74c3c; }
    </style>
</head>
<body>
    <div class="container">
        <h1>⏱️ Test des Sessions</h1>
        
        <div class="box">
            <p><strong>Session ID :</strong> <code><%= request.getAttribute("sessionId") %></code></p>
            <p><strong>Nombre de visites :</strong> <span style="font-size: 24px; color: #27ae60;"><%= request.getAttribute("compteur") %></span></p>
            
            <hr style="margin: 20px 0;">
            
            <% String username = (String) request.getAttribute("username"); %>
            <% if (username != null) { %>
                <p>👤 Connecté en tant que : <strong><%= username %></strong></p>
                <a href="/app-test/sprint9/logout" class="btn btn-red">Se déconnecter</a>
            <% } else { %>
                <p>👤 Vous n'êtes pas connecté</p>
                <form action="/app-test/sprint9/login" method="POST">
                    <input type="text" name="nom" placeholder="Votre nom" required>
                    <button type="submit" class="btn">Connexion</button>
                </form>
            <% } %>
        </div>
        
        <a href="/app-test/sprint9" class="btn">🔄 Recharger la page</a>
    </div>
</body>
</html>