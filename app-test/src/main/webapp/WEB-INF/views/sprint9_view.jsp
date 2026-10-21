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
        .btn { display: inline-block; padding: 10px 20px; background: #8e44ad; color: white; text-decoration: none; border-radius: 6px; margin-top: 10px; }
        .btn-red { background: #e74c3c; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Test des Sessions</h1>
        
        <div class="box">
            <h3>Votre session ID :</h3>
            <p><code><%= request.getAttribute("sessionId") %></code></p>
            
            <h3>Nombre de visites :</h3>
            <h2 style="color: #27ae60; font-size: 40px;"><%= request.getAttribute("compteur") %></h2>
            
            <h3>Dernière visite :</h3>
            <p><%= request.getAttribute("derniereVisite") %></p>
        </div>
        
        <p><em>Rechargez cette page : le compteur augmente sans changer l'ID de session.</em></p>
        
        <a href="/app-test/sprint9" class="btn">Recharger</a>
        <a href="/app-test/sprint9/reset" class="btn btn-red">Réinitialiser la session</a>
    </div>
</body>
</html>