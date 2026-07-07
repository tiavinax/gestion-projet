<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*, mg.itu.framework.UrlMethod, mg.itu.framework.MethodInfo" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Sprint 2 - @RequestMapping</title>
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
                    border-bottom: 3px solid #27ae60;
                    padding-bottom: 15px;
                    margin-bottom: 20px;
                }

                .badge {
                    display: inline-block;
                    background: #27ae60;
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
                    padding: 15px 20px;
                    margin-bottom: 10px;
                    border-radius: 6px;
                    border-left: 4px solid #27ae60;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }

                .item .url {
                    font-weight: 600;
                    color: #3498db;
                    font-family: monospace;
                }

                .item .controller {
                    color: #2c3e50;
                }

                .item .method {
                    color: #27ae60;
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
            </style>
        </head>

        <body>
            <div class="container">
                <h1>
                    🗺️ <%= request.getAttribute("sprintName") %>
                        <span class="badge">Sprint 2</span>
                </h1>
                <p style="color: #7f8c8d; margin-bottom: 20px;">
                    <%= request.getAttribute("sprintDesc") %>
                </p>

                <div class="info-bar">
                    <span> URLs : <strong>
                            <%= request.getAttribute("totalUrls") %>
                        </strong></span>
                    <span>
                        <%= request.getAttribute("serverTime") %>
                    </span>
                </div>

                <% Map<UrlMethod, MethodInfo> urlMapping = (Map<UrlMethod, MethodInfo>)
                        request.getAttribute("urlMapping");
                        if (urlMapping == null || urlMapping.isEmpty()) {
                        %>
                        <div class="empty"> Aucun mapping trouvé</div>
                        <% } else { %>
                            <ul class="list">
                                <% int i=1; for (Map.Entry<UrlMethod, MethodInfo> entry : urlMapping.entrySet()) {
                                    UrlMethod key = entry.getKey();
                                    MethodInfo info = entry.getValue();
                                    %>
                                    <li class="item">
                                        <div>
                                            <span class="url">
                                                <%= i++ %>. <%= key.getUrl() %>
                                            </span>
                                            <span style="color: #95a5a6; margin: 0 8px;">→</span>
                                            <span class="controller">
                                                <%= info.getControllerName() %>
                                            </span>
                                            <span style="color: #95a5a6;">.</span>
                                            <span class="method">
                                                <%= info.getMethodName() %>()
                                            </span>
                                        </div>
                                        <span style="font-size: 12px; color: #27ae60;">✅</span>
                                    </li>
                                    <% } %>
                            </ul>
                            <% } %>

                                <a href="/app-test/" class="btn-back"> Retour à l'accueil</a>
                                <div class="footer"><strong>Framework MVC Artisanal</strong> &bull; Sprint 2</div>
            </div>
        </body>

        </html>