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
        .badge-green { background: #27ae60; }
        .badge-red { background: #e74c3c; }
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
        .error-box {
            background: #fde8e8;
            border-left: 4px solid #e74c3c;
            padding: 15px 20px;
            border-radius: 6px;
            margin-bottom: 20px;
        }
        .error-box strong { color: #e74c3c; }
        .error-box code { background: white; padding: 2px 8px; border-radius: 4px; }
        .url-list { list-style: none; }
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
        .url-item:hover { background: #eef2f7; transform: translateX(5px); }
        .url-path {
            font-weight: 600;
            color: #3498db;
            font-size: 16px;
            font-family: 'Courier New', monospace;
        }
        .url-controller { color: #2c3e50; font-weight: 500; }
        .url-method { color: #27ae60; font-weight: 500; }
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
        }
        .current-url code { background: white; padding: 2px 8px; border-radius: 4px; color: #3498db; }
        .test-link {
            display: inline-block;
            background: #27ae60;
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
        }
        .test-link:hover { background: #219a52; }
        .success-msg {
            background: #e8f8ed;
            border-left: 4px solid #27ae60;
            padding: 12px 18px;
            border-radius: 6px;
            margin-bottom: 20px;
            color: #1a6e38;
        }
        .all-urls-msg {
            background: #fff3cd;
            border-left: 4px solid #ffc107;
            padding: 12px 18px;
            border-radius: 6px;
            margin-bottom: 20px;
            color: #856404;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            URL Mappings 
            <span class="badge">Sprint 2</span>
            <span class="badge badge-green">@RequestMapping</span>
        </h1>
        
        <%
            boolean isError = (Boolean) request.getAttribute("isError");
            boolean isSingle = (Boolean) request.getAttribute("isSingle");
            String currentUrl = (String) request.getAttribute("currentUrl");
            String invalidUrl = (String) request.getAttribute("invalidUrl");
            Map<String, MethodInfo> urlMapping = (Map<String, MethodInfo>) request.getAttribute("urlMapping");
            
            // CAS 1 : URL INVALIDE → Afficher toutes les URLs avec message d'erreur
            if (isError) {
        %>
            <div class="error-box">
                <strong>URL non supportée :</strong> <code><%= invalidUrl %></code>
                <p style="margin-top: 8px; font-size: 14px; color: #555;">
                    Voici toutes les URLs supportées par l'application :
                </p>
            </div>
            
        <%
            // CAS 2 : URL VALIDE (single) → Afficher uniquement cette URL
            } else if (isSingle && currentUrl != null) {
        %>
            <div class="success-msg">
                URL supportée : <code><%= currentUrl %></code>
            </div>
            
        <%
            // CAS 3 : Page d'accueil → Afficher toutes les URLs
            } else {
        %>
            <div class="all-urls-msg">
                Toutes les URLs supportées par l'application :
            </div>
        <%
            }
        %>
        
        <%
            if (urlMapping == null || urlMapping.isEmpty()) {
        %>
            <div style="text-align: center; padding: 50px 20px; color: #7f8c8d;">
                <h3>Aucun mapping trouvé</h3>
                <p>Ajoutez l'annotation <code>@RequestMapping</code> à vos méthodes.</p>
            </div>
        <%
            } else {
        %>
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
            <%= (urlMapping != null) ? urlMapping.size() : 0 %> URL(s)
        </div>
    </div>
</body>
</html>