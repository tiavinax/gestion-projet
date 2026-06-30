<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, mg.itu.framework.UrlMethod, mg.itu.framework.MethodInfo" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Erreur de Mapping - Sprint 3</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f5f7fa;
            padding: 40px;
            color: #333;
        }
        .container {
            max-width: 900px;
            margin: 0 auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.08);
            padding: 40px;
        }
        h1 {
            color: #e74c3c;
            font-size: 28px;
            border-bottom: 3px solid #e74c3c;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .badge {
            display: inline-block;
            background: #e74c3c;
            color: white;
            padding: 4px 14px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 600;
        }
        .error-box {
            background: #fde8e8;
            border-left: 4px solid #e74c3c;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .error-box h3 { color: #e74c3c; margin-bottom: 10px; }
        .error-box code { background: white; padding: 2px 8px; border-radius: 4px; }
        .conflict-list {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
        }
        .conflict-item {
            background: #fff;
            padding: 12px 15px;
            margin-bottom: 8px;
            border-radius: 4px;
            border-left: 3px solid #e74c3c;
            font-family: 'Courier New', monospace;
            font-size: 14px;
            color: #c0392b;
        }
        .conflict-item:last-child { margin-bottom: 0; }
        .btn-back {
            display: inline-block;
            background: #3498db;
            color: white;
            padding: 10px 25px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
            margin-top: 20px;
        }
        .btn-back:hover { background: #2980b9; }
        .footer {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #ecf0f1;
            font-size: 13px;
            color: #95a5a6;
            text-align: center;
        }
        .url-mapping {
            background: #e8f4fd;
            padding: 10px 15px;
            border-radius: 4px;
            margin: 5px 0;
            font-family: 'Courier New', monospace;
            font-size: 13px;
        }
        .url-mapping strong { color: #3498db; }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            Erreur de Mapping 
            <span class="badge">Sprint 3</span>
        </h1>
        
        <div class="error-box">
            <h3>Conflit de mapping détecté</h3>
            <p>
                Plusieurs méthodes utilisent la même URL et la même méthode HTTP.
                <br><br>
                <strong>URL demandée :</strong> <code><%= request.getAttribute("invalidUrl") %></code>
                <br>
                <strong>Méthode HTTP :</strong> <code><%= request.getAttribute("requestedMethod") %></code>
            </p>
        </div>
        
        <div class="conflict-list">
            <h3 style="color: #e74c3c; margin-bottom: 15px;">Conflits détectés :</h3>
            <%
                List<String> mappingErrors = (List<String>) request.getAttribute("mappingErrors");
                if (mappingErrors != null && !mappingErrors.isEmpty()) {
                    for (String error : mappingErrors) {
            %>
                <div class="conflict-item">❌ <%= error %></div>
            <%
                    }
                }
            %>
        </div>
        
        <div style="background: #fff3cd; padding: 15px; border-radius: 8px; margin-top: 20px;">
            <h4 style="color: #856404;">Solution :</h4>
            <ul style="margin-top: 10px; padding-left: 20px; color: #856404;">
                <li>Vérifiez que chaque URL + méthode HTTP est unique</li>
                <li>Utilisez des URLs différentes pour chaque méthode</li>
                <li>Utilisez des méthodes HTTP différentes (GET, POST, etc.)</li>
            </ul>
        </div>
        
        <div style="margin-top: 20px; text-align: center;">
            <a href="<%= request.getContextPath() %>/" class="btn-back">🏠 Retour à l'accueil</a>
        </div>
        
        <div class="footer">
            <strong>Framework MVC Artisanal</strong> &bull; Sprint 3 - Gestion des conflits
        </div>
    </div>
</body>
</html>