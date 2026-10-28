<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profil - Sprint 10</title>
</head>
<body style="font-family: sans-serif; padding: 40px;">

    <h2>Page Profil (Protégée)</h2>
    
    <p>Bienvenue, <strong><%= request.getAttribute("user") %></strong> !</p>
    <p>Vous voyez cette page car la variable "user" existe dans la session.</p>

    <br>
    <a href="/app-test/auth/logout">Se déconnecter</a>

</body>
</html>