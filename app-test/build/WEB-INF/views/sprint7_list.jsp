<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.test.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des utilisateurs</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f7fa; padding: 40px; }
        .container { max-width: 700px; margin: 0 auto; background: white; border-radius: 12px; padding: 40px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        h1 { color: #2c3e50; border-bottom: 3px solid #3498db; padding-bottom: 15px; margin-bottom: 20px; }
        .badge { display: inline-block; background: #27ae60; color: white; padding: 4px 14px; border-radius: 20px; font-size: 14px; font-weight: 600; }
        .success { background: #e8f8ed; border-left: 4px solid #27ae60; padding: 12px 18px; border-radius: 6px; margin-bottom: 20px; color: #1a6e38; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ecf0f1; }
        th { background: #f8f9fa; color: #2c3e50; font-weight: 600; }
        tr:hover { background: #f8f9fa; }
        .actions { margin-top: 25px; display: flex; gap: 10px; }
        .btn { display: inline-block; padding: 10px 25px; border-radius: 6px; text-decoration: none; font-weight: 500; }
        .btn-add { background: #27ae60; color: white; }
        .btn-add:hover { background: #219a52; }
        .btn-back { background: #3498db; color: white; }
        .btn-back:hover { background: #2980b9; }
    </style>
</head>
<body>
    <div class="container">
        <h1>👥 Liste des utilisateurs <span class="badge">Sprint 7</span></h1>
        
        <%
            String message = (String) request.getAttribute("message");
            if (message != null && !message.isEmpty()) {
        %>
            <div class="success">✅ <%= message %></div>
        <%
            }
        %>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<User> users = (List<User>) request.getAttribute("users");
                    if (users != null && !users.isEmpty()) {
                        for (User user : users) {
                %>
                    <tr>
                        <td><%= user.getId() %></td>
                        <td><strong><%= user.getName() %></strong></td>
                        <td><%= user.getEmail() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr><td colspan="3" style="text-align: center; color: #95a5a6;">Aucun utilisateur</td></tr>
                <%
                    }
                %>
            </tbody>
        </table>
        
        <div class="actions">
            <a href="/app-test/user/add" class="btn btn-add">➕ Ajouter</a>
            <a href="/app-test/" class="btn btn-back">🏠 Accueil</a>
        </div>
    </div>
</body>
</html>