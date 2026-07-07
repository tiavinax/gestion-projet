<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*, mg.itu.framework.UrlMethod, mg.itu.framework.MethodInfo" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Sprint 4 - ContextListener</title>
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
                }

                .container {
                    max-width: 900px;
                    margin: 0 auto;
                    background: white;
                    border-radius: 12px;
                    padding: 40px;
                    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
                }

                h1 {
                    color: #2c3e50;
                    border-bottom: 3px solid #8e44ad;
                    padding-bottom: 15px;
                    margin-bottom: 20px;
                }

                .badge {
                    display: inline-block;
                    background: #8e44ad;
                    color: white;
                    padding: 4px 14px;
                    border-radius: 20px;
                    font-size: 14px;
                    font-weight: 600;
                }

                .info-bar {
                    background: #ecf0f1;
                    padding: 15px 20px;
                    border-radius: 8px;
                    margin-bottom: 25px;
                    display: flex;
                    justify-content: space-between;
                    flex-wrap: wrap;
                    gap: 10px;
                }

                .list {
                    list-style: none;
                }

                .item {
                    background: #f8f9fa;
                    padding: 12px 18px;
                    margin-bottom: 8px;
                    border-radius: 6px;
                    border-left: 4px solid #8e44ad;
                    font-family: monospace;
                    font-size: 14px;
                }

                .btn-back {
                    display: inline-block;
                    background: #3498db;
                    color: white;
                    padding: 10px 25px;
                    border-radius: 6px;
                    text-decoration: none;
                    margin-top: 20px;
                }

                .btn-back:hover {
                    background: #2980b9;
                }

                .footer {
                    margin-top: 30px;
                    padding-top: 20px;
                    border-top: 1px solid #ecf0f1;
                    text-align: center;
                    color: #95a5a6;
                    font-size: 13px;
                }

                .empty {
                    text-align: center;
                    padding: 40px;
                    color: #7f8c8d;
                }

                .error-box {
                    background: #fde8e8;
                    border-left: 4px solid #e74c3c;
                    padding: 15px;
                    border-radius: 6px;
                    margin-bottom: 20px;
                }

                .error-box li {
                    color: #c0392b;
                    font-family: monospace;
                    font-size: 13px;
                    padding: 4px 0;
                }

                .success-box {
                    background: #e8f8ed;
                    border-left: 4px solid #27ae60;
                    padding: 15px;
                    border-radius: 6px;
                    margin-bottom: 20px;
                    color: #1a6e38;
                }
            </style>
        </head>

        <body>
            <div class="container">
                <h1>
                    ⚙️ <%= request.getAttribute("sprintName") %>
                        <span class="badge">Sprint 4</span>
                </h1>
                <p style="color: #7f8c8d; margin-bottom: 20px;">
                    <%= request.getAttribute("sprintDesc") %>
                </p>

                <div class="info-bar">
                    <span> Contrôleurs : <strong>
                            <%= request.getAttribute("totalControllers") %>
                        </strong></span>
                    <span> URLs : <strong>
                            <%= request.getAttribute("totalUrls") %>
                        </strong></span>
                    <span>
                        <%= request.getAttribute("serverTime") %>
                    </span>
                </div>

                <% List<String> mappingErrors = (List<String>) request.getAttribute("mappingErrors");
                        if (mappingErrors != null && !mappingErrors.isEmpty()) {
                        %>
                        <div class="error-box">
                            <strong> Conflits détectés :</strong>
                            <ul style="margin-top: 8px; list-style: none; padding-left: 0;">
                                <% for (String error : mappingErrors) { %>
                                    <li>❌ <%= error %>
                                    </li>
                                    <% } %>
                            </ul>
                        </div>
                        <% } else { %>
                            <div class="success-box">✅ Aucun conflit de mapping détecté</div>
                            <% } %>

                                <div style="background: #f8f9fa; padding: 15px; border-radius: 8px; margin-top: 10px;">
                                    <p style="font-size: 14px; color: #555;">
                                        <strong> Initialisation :</strong> FrameworkInitializer exécuté au démarrage
                                    </p>
                                    <p style="font-size: 14px; color: #555; margin-top: 5px;">
                                        <strong> Stockage :</strong> Données disponibles dans ServletContext
                                    </p>
                                </div>

                                <a href="/app-test/" class="btn-back"> Retour à l'accueil</a>
                                <div class="footer"><strong>Framework MVC Artisanal</strong> &bull; Sprint 4</div>
            </div>
        </body>

        </html>