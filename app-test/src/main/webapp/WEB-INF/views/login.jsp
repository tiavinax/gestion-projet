<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
</head>
<body style="font-family: sans-serif; padding: 40px;">

    <h2>Connexion (Sprint 10)</h2>
    
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="/app-test/auth/do-login" method="POST">
        <label>Nom d'utilisateur :</label><br>
        <input type="text" name="username" required>
        <br><br>
        <button type="submit">Se connecter</button>
    </form>
    
    <p style="font-size: 13px; color: gray;">
        (Astuce : tapez "admin" pour avoir le rôle administrateur, sinon "user").
    </p>

</body>
</html>