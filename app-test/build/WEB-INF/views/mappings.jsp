<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, mg.itu.framework.UrlMethod, mg.itu.framework.MethodInfo" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>URL Mappings - Sprint 3</title>
    <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> -->
    <link rel="stylesheet" href="/app-test/css/style.css">
    <style>
         * {
     margin: 0;
     padding: 0;
     box-sizing: border-box;
 }

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
     box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
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

 .badge-red {
     background: #e74c3c;
 }

 .badge-orange {
     background: #e67e22;
 }

 .badge-purple {
     background: #8e44ad;
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

 .error-box {
     background: #fde8e8;
     border-left: 4px solid #e74c3c;
     padding: 15px 20px;
     border-radius: 6px;
     margin-bottom: 20px;
 }

 .error-box strong {
     color: #e74c3c;
 }

 .error-box code {
     background: white;
     padding: 2px 8px;
     border-radius: 4px;
 }

 .method-tag {
     display: inline-block;
     padding: 2px 10px;
     border-radius: 12px;
     font-size: 12px;
     font-weight: 600;
     margin-right: 8px;
 }

 .method-get {
     background: #d5f5e3;
     color: #1a7a3a;
 }

 .method-post {
     background: #fdebd0;
     color: #a04000;
 }

 .method-put {
     background: #d6eaf8;
     color: #1a5276;
 }

 .method-delete {
     background: #fadbd8;
     color: #922b21;
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

 .url-method-name {
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
 }

 .test-link:hover {
     background: #219a52;
 }

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

 .btn-back {
     display: inline-block;
     background: #3498db;
     color: white;
     padding: 8px 16px;
     border-radius: 6px;
     text-decoration: none;
     font-size: 14px;
     margin-top: 10px;
 }

 .btn-back:hover {
     background: #2980b9;
 }

 .method-error-box {
     background: #fdebd0;
     border-left: 4px solid #e67e22;
     padding: 15px 20px;
     border-radius: 6px;
     margin-bottom: 20px;
 }

 .method-error-box strong {
     color: #e67e22;
 }

 .available-method {
     display: inline-block;
     background: #e8f4fd;
     padding: 4px 14px;
     border-radius: 12px;
     margin: 0 5px;
     font-weight: 600;
     font-size: 13px;
 }

 .form-test {
     background: #f8f9fa;
     padding: 20px;
     border-radius: 8px;
     margin: 20px 0;
     text-align: center;
 }

 .form-test input[type="submit"] {
     background: #e67e22;
     color: white;
     padding: 10px 30px;
     border: none;
     border-radius: 6px;
     cursor: pointer;
     font-size: 16px;
 }

 .form-test input[type="submit"]:hover {
     background: #d35400;
 }

 .message-box {
     background: #e8f4fd;
     border-left: 4px solid #3498db;
     padding: 15px 20px;
     border-radius: 6px;
     margin-bottom: 20px;
 }

 .message-box h3 {
     color: #2c3e50;
     margin-bottom: 5px;
 }

 .message-box .msg-content {
     color: #555;
     font-size: 15px;
 }

 .message-box .msg-meta {
     color: #7f8c8d;
     font-size: 12px;
     margin-top: 8px;
 }

 .status-success {
     border-left-color: #27ae60;
 }

 .status-info {
     border-left-color: #3498db;
 }

 .status-warning {
     border-left-color: #e67e22;
 }
    </style>>
</head>
<body>
    <div class="container">
        <h1>
            URL Mappings
            <span class="badge">Sprint 3</span>
            <span class="badge badge-green">@RequestMapping</span>
        </h1>

        <div class="info-bar">
            <div>
                <span>Total URLs : <strong><%= ((Map<?, ?>) request.getAttribute("urlMapping")).size() %></strong></span>
                <span style="margin-left: 15px;">Contrôleurs : <strong><%= request.getAttribute("totalControllers") %></strong></span>
            </div>
        </div>

        <%
            // Récupération des attributs
            String message = (String) request.getAttribute("message");
            String fomba = (String) request.getAttribute("fomba");
            String status = (String) request.getAttribute("status");
            String nom = (String) request.getAttribute("nom");
            Date timestamp = (Date) request.getAttribute("timestamp");
            
            Boolean isError = (Boolean) request.getAttribute("isError");
            Boolean isSingle = (Boolean) request.getAttribute("isSingle");
            Boolean isMethodError = (Boolean) request.getAttribute("isMethodError");
            String currentUrl = (String) request.getAttribute("currentUrl");
            String currentMethod = (String) request.getAttribute("currentMethod");
            String invalidUrl = (String) request.getAttribute("invalidUrl");
            String requestedMethod = (String) request.getAttribute("requestedMethod");
            List<String> availableMethods = (List<String>) request.getAttribute("availableMethods");
            
            Map<UrlMethod, MethodInfo> urlMapping = (Map<UrlMethod, MethodInfo>) request.getAttribute("urlMapping");
            String contextPath = request.getContextPath();
            
            // Affichage du message du contrôleur
            if (message != null && !message.isEmpty()) {
                String statusClass = "status-success";
                if ("info".equals(status)) statusClass = "status-info";
                else if ("warning".equals(status)) statusClass = "status-warning";
        %>
            <div class="message-box <%= statusClass %>">
                <h3>Message du contrôleur</h3>
                <div class="msg-content"><%= message %></div>
                <% if (fomba != null) { %>
                    <div class="msg-meta">Méthode : <strong><%= fomba %></strong></div>
                <% } %>
                <% if (nom != null) { %>
                    <div class="msg-meta">Nom reçu : <strong><%= nom %></strong></div>
                <% } %>
                <% if (timestamp != null) { %>
                    <div class="msg-meta">Timestamp : <%= timestamp %></div>
                <% } %>
            </div>
        <%
            }
            
            // CAS 1 : ERREUR METHODE HTTP NON SUPPORTEE
            if (isMethodError != null && isMethodError) {
        %>
            <div class="method-error-box">
                <strong>Méthode HTTP non supportée :</strong>
                <code><%= requestedMethod %></code> pour <code><%= invalidUrl %></code>
                <p style="margin-top: 8px; font-size: 14px; color: #555;">
                    Méthodes supportées :
                    <%
                        if (availableMethods != null) {
                            for (String method : availableMethods) {
                                String cls = "available-method";
                                if ("GET".equals(method)) cls += " method-get";
                                else if ("POST".equals(method)) cls += " method-post";
                                else if ("PUT".equals(method)) cls += " method-put";
                                else if ("DELETE".equals(method)) cls += " method-delete";
                    %>
                                <span class="<%= cls %>"><%= method %></span>
                    <%
                            }
                        }
                    %>
                </p>
            </div>
        <%
            // CAS 2 : URL INVALIDE
            } else if (isError != null && isError) {
        %>
            <div class="error-box">
                <strong>URL non supportée :</strong> <code><%= invalidUrl %></code>
                <p style="margin-top: 8px; font-size: 14px; color: #555;">
                    Voici toutes les URLs supportées par l'application :
                </p>
            </div>
        <%
            // CAS 3 : URL VALIDE (single)
            } else if (isSingle != null && isSingle && currentUrl != null) {
        %>
            <div class="success-msg">
                URL supportée : <code><%= currentUrl %></code>
                <% if (currentMethod != null) { %>
                    <span style="margin-left: 10px; font-weight: normal; color: #555;">
                        (Méthode : <strong><%= currentMethod %></strong>)
                    </span>
                <% } %>
            </div>
        <%
            // CAS 4 : Page d'accueil
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
                    for (Map.Entry<UrlMethod, MethodInfo> entry : urlMapping.entrySet()) {
                        UrlMethod key = entry.getKey();
                        MethodInfo info = entry.getValue();
                        String fullUrl = contextPath + key.getUrl();
                        
                        String methodClass = "method-get";
                        if ("POST".equals(key.getMethod())) {
                            methodClass = "method-post";
                        } else if ("PUT".equals(key.getMethod())) {
                            methodClass = "method-put";
                        } else if ("DELETE".equals(key.getMethod())) {
                            methodClass = "method-delete";
                        }
                %>
                    <li class="url-item">
                        <div style="display: flex; align-items: center; gap: 15px; flex: 1;">
                            <span class="url-number"><%= index++ %></span>
                            <div>
                                <div>
                                    <span class="url-path"><%= key.getUrl() %></span>
                                    <span class="method-tag <%= methodClass %>"><%= key.getMethod() %></span>
                                </div>
                                <div style="margin-top: 4px;">
                                    <span class="url-controller"><%= info.getControllerName() %></span>
                                    <span style="color: #95a5a6;">.</span>
                                    <span class="url-method-name"><%= info.getMethodName() %>()</span>
                                </div>
                            </div>
                        </div>
                        <a href="<%= fullUrl %>" class="test-link">Tester (GET)</a>
                    </li>
                <%
                    }
                %>
            </ul>
        <%
            }
        %>

        <!-- Formulaire pour tester POST -->
        <div class="form-test">
            <h3>Tester POST</h3>
            <form action="/app-test/app/hello" method="POST">
                <label style="margin-right: 10px;">Nom :</label>
                <input type="text" name="nom" placeholder="Votre nom" style="padding: 8px 12px; border-radius: 4px; border: 1px solid #ddd; margin-right: 10px;">
                <input type="submit" value="Envoyer">
            </form>
        </div>

        <div style="margin-top: 20px; text-align: center;">
            <a href="<%= contextPath %>/" class="btn-back">Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>