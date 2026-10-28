<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord Admin</title>
</head>
<body style="font-family: sans-serif; padding: 40px;">

    <h2>Tableau de bord Administrateur</h2>
    
    <p>Bienvenue <strong><%= request.getAttribute("user") %></strong> !</p>
    <p>Vous voyez cette page car vous avez le rôle <strong>admin</strong>.</p>

    <br>
    <a href="/app-test/profile">Aller au profil utilisateur</a><br>
    <a href="/app-test/auth/logout">Se déconnecter</a>

</body>
</html>