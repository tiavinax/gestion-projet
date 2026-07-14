<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<% List<String> users = (List<String>) request.getAttribute("users"); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sprint 5b</title>
</head>
<body>
    <div class="container">
        <h1>Repository</h1>
        <div class="card">
            <h3>Utilisateurs</h3>
            <ul>
                <%
                    if (users != null) {
                        for (String user : users) {
                %>
                    <li><%= user %></li>
                <%
                        }
                    }
                %>
            </ul>
        </div>
        <a href="/app-test/" class="btn-back">Retour à l'accueil</a>
    </div>
</body>
</html>