<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Erreur 404</title>
    <style>
        body { font-family: Arial; text-align: center; padding: 50px; background: #f5f7fa; }
        .container { max-width: 600px; margin: 0 auto; background: white; padding: 40px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        h1 { color: #e74c3c; }
        .btn { display: inline-block; background: #3498db; color: white; padding: 10px 25px; border-radius: 6px; text-decoration: none; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>❌ 404 - Page non trouvée</h1>
        <p>L'URL <strong><%= request.getAttribute("invalidUrl") %></strong> n'existe pas.</p>
        <a href="/app-test/" class="btn">Retour à l'accueil</a>
    </div>
</body>
</html>