<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accès refusé</title>
</head>
<body style="font-family: sans-serif; padding: 40px;">

    <h2>403 - Accès refusé</h2>
    
    <p><%= request.getAttribute("error") %></p>

    <br>
    <a href="/app-test/auth/login">Retourner à la page de connexion</a>

</body>
</html>