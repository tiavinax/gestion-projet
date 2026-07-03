<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, mg.itu.framework.MethodInfo" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>URL Mappings - Sprint 2</title>
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
            color: #2c3e50;
            font-size: 28px;
            border-bottom: 3px solid #3498db;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .badge {
            display: inline-block;
            background: #3498db;
            color: white;
            padding: 4px 14px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 600;
        }
        .badge-green {
            background: #27ae60;
        }
        .info-bar {
            background: #ecf0f1;
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 10px;
        }
        .info-bar span {
            font-size: 14px;
            color: #555;
        }
        .url-list {
            list-style: none;
        }
        .url-item {
            background: #f8f9fa;
            border-left: 4px solid #3498db;
            padding: 15px 20px;
            margin-bottom: 10px;
            border-radius: 6px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            transition: all 0.2s;
        }
        .url-item:hover {
            background: #eef2f7;
            transform: translateX(5px);
        }
        .url-path {
            font-weight: 600;
            color: #3498db;
            font-size: 16px;
            font-family: 'Courier New', monospace;
        }
        .url-controller {
            color: #2c3e50;
            font-weight: 500;
        }
        .url-method {
            color: #27ae60;
            font-weight: 500;
        }
        .url-number {
            background: #3498db;
            color: white;
            width: 28px;
            height: 28px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 13px;
            font-weight: bold;
            flex-shrink: 0;
        }
        .footer {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #ecf0f1;
            font-size: 13px;
            color: #95a5a6;
            text-align: center;
        }
        .current-url {
            background: #e8f4fd;
            padding: 10px 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            color: #2c3e50;
        }
        .current-url code {
            background: white;
            padding: 2px 8px;
            border-radius: 4px;
            color: #3498db;
        }
        .test-link {
            display: inline-block;
            background: #27ae60;
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
            margin: 5px;
        }
        .test-link:hover {
            background: #219a52;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            🗺️ URL Mappings 
            <span class="badge">Sprint 2</span>
            <span class="badge badge-green">@RequestMapping</span>
        </h1>
        
        <div class="info-bar">
            <div>
                <span>📦 Total URLs : <strong><%= ((Map<String, MethodInfo>) request.getAttribute("urlMapping")).size() %></strong></span>
                <span style="margin-left: 15px;">📋 Contrôleurs : <strong><%= request.getAttribute("totalControllers") %></strong></span>
            </div>
            <div>
                <span class="time">🕐 <%= new java.util.Date() %></span>
            </div>
        </div>
        
        <% 
            String currentUrl = (String) request.getAttribute("currentUrl");
            if (currentUrl != null && !currentUrl.isEmpty()) {
        %>
            <div class="current-url">
                🔍 URL actuelle : <code><%= currentUrl %></code>
            </div>
        <% } %>
        
        <%
            Map<String, MethodInfo> urlMapping = (Map<String, MethodInfo>) request.getAttribute("urlMapping");
            if (urlMapping == null || urlMapping.isEmpty()) {
        %>
            <div style="text-align: center; padding: 50px 20px; color: #7f8c8d;">
                <h3>⚠️ Aucun mapping trouvé</h3>
                <p>Ajoutez l'annotation <code>@RequestMapping</code> à vos méthodes.</p>
            </div>
        <%
            } else {
        %>
            <h3 style="margin-bottom: 15px;">📋 Liste des URLs supportées</h3>
            <ul class="url-list">
                <%
                    int index = 1;
                    for (Map.Entry<String, MethodInfo> entry : urlMapping.entrySet()) {
                        MethodInfo info = entry.getValue();
                %>
                    <li class="url-item">
                        <div style="display: flex; align-items: center; gap: 15px; flex: 1;">
                            <span class="url-number"><%= index++ %></span>
                            <div>
                                <div class="url-path"><%= entry.getKey() %></div>
                                <div>
                                    <span class="url-controller"><%= info.getControllerName() %></span>
                                    <span style="color: #95a5a6;">.</span>
                                    <span class="url-method"><%= info.getMethodName() %>()</span>
                                </div>
                            </div>
                        </div>
                        <a href="<%= entry.getKey() %>" class="test-link">Tester</a>
                    </li>
                <%
                    }
                %>
            </ul>
        <%
            }
        %>
        
        <div class="footer">
            <strong>Framework MVC Artisanal</strong> &bull; Sprint 2 - @RequestMapping &bull; 
            <%= (urlMapping != null) ? urlMapping.size() : 0 %> URL(s) supportée(s)
        </div>
    </div>
</body>
</html>