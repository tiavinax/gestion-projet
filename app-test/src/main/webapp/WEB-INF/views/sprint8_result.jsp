<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.framework.MapFile, com.test.model.User, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sprint 8 - Résultat upload</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f7fa; padding: 40px; }
        .container { max-width: 800px; margin: 0 auto; background: white; border-radius: 12px; padding: 40px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        h1 { color: #2c3e50; border-bottom: 3px solid #27ae60; padding-bottom: 15px; margin-bottom: 20px; }
        .badge { display: inline-block; background: #27ae60; color: white; padding: 4px 14px; border-radius: 20px; font-size: 14px; font-weight: 600; }
        .success { background: #e8f8ed; border-left: 4px solid #27ae60; padding: 12px 18px; border-radius: 6px; margin-bottom: 20px; color: #1a6e38; font-weight: 500; }
        .info-block { background: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 15px; border-left: 4px solid #3498db; }
        .info-block h3 { color: #2c3e50; margin-bottom: 12px; font-size: 16px; }
        .info-row { padding: 8px 0; border-bottom: 1px solid #ecf0f1; }
        .info-row:last-child { border-bottom: none; }
        .info-label { display: inline-block; width: 140px; color: #7f8c8d; font-weight: 500; font-size: 14px; }
        .info-value { color: #2c3e50; font-weight: 500; font-family: monospace; font-size: 14px; }
        .file-card { background: white; padding: 15px; border-radius: 6px; margin-bottom: 10px; border: 1px solid #ecf0f1; }
        .file-icon { display: inline-block; background: #16a085; color: white; padding: 4px 10px; border-radius: 4px; font-size: 12px; font-weight: 600; text-transform: uppercase; margin-right: 10px; }
        .btn-back { display: inline-block; background: #3498db; color: white; padding: 10px 25px; border-radius: 6px; text-decoration: none; margin-top: 20px; }
        .btn-back:hover { background: #2980b9; }
        .footer { margin-top: 30px; padding-top: 20px; border-top: 1px solid #ecf0f1; text-align: center; color: #95a5a6; font-size: 13px; }
        code { background: #f8f9fa; padding: 2px 8px; border-radius: 4px; font-size: 13px; color: #c0392b; }
    </style>
</head>
<body>
    <div class="container">
        <h1>✅ Upload réussi <span class="badge">Sprint 8</span></h1>
        
        <%
            String mode = (String) request.getAttribute("mode");
        %>
        
        <div class="success">
            ✅ Fichier(s) uploadé(s) avec succès — Mode : 
            <strong>
                <% if ("single".equals(mode)) { %>MapFile simple<% } %>
                <% if ("multiple".equals(mode)) { %>MapFile[] multiple<% } %>
                <% if ("object".equals(mode)) { %>Objet User avec MapFile<% } %>
            </strong>
        </div>
        
        <!-- ============================================ -->
        <!-- MODE 1 : SINGLE                              -->
        <!-- ============================================ -->
        <% if ("single".equals(mode)) { 
            MapFile fichier = (MapFile) request.getAttribute("fichier");
        %>
            <div class="info-block">
                <h3>Informations</h3>
                <div class="info-row">
                    <span class="info-label">Nom :</span>
                    <span class="info-value"><%= request.getAttribute("nom") %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Email :</span>
                    <span class="info-value"><%= request.getAttribute("email") %></span>
                </div>
            </div>
            
            <% if (fichier != null) { %>
                <div class="info-block">
                    <h3>Fichier uploadé</h3>
                    <div class="file-card">
                        <span class="file-icon"><%= fichier.getExtension() %></span>
                        <strong><%= fichier.getFileName() %></strong>
                        <div style="margin-top: 8px;">
                            <div class="info-row">
                                <span class="info-label">Taille :</span>
                                <span class="info-value"><%= fichier.getSizeFormatted() %></span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Extension :</span>
                                <span class="info-value"><%= fichier.getExtension() %></span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Chemin :</span>
                                <span class="info-value"><%= request.getAttribute("savedPath") %></span>
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
        <% } %>
        
        <!-- ============================================ -->
        <!-- MODE 2 : MULTIPLE                            -->
        <!-- ============================================ -->
        <% if ("multiple".equals(mode)) { 
            MapFile[] fichiers = (MapFile[]) request.getAttribute("fichiers");
        %>
            <div class="info-block">
                <h3>Fichiers uploadés (<%= fichiers != null ? fichiers.length : 0 %>)</h3>
                <% if (fichiers != null && fichiers.length > 0) { 
                    for (int i = 0; i < fichiers.length; i++) { 
                        MapFile f = fichiers[i];
                %>
                    <div class="file-card">
                        <span class="file-icon"><%= f.getExtension() %></span>
                        <strong><%= f.getFileName() %></strong>
                        <div style="margin-top: 8px; font-size: 13px; color: #7f8c8d;">
                            Taille : <%= f.getSizeFormatted() %> — 
                            Extension : <%= f.getExtension() %>
                        </div>
                    </div>
                <% } } %>
            </div>
        <% } %>
        
        <!-- ============================================ -->
        <!-- MODE 3 : OBJECT                              -->
        <!-- ============================================ -->
        <% if ("object".equals(mode)) { 
            User user = (User) request.getAttribute("user");
        %>
            <div class="info-block">
                <h3>Utilisateur (objet User)</h3>
                <div class="info-row">
                    <span class="info-label">ID :</span>
                    <span class="info-value"><%= user.getId() %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Nom :</span>
                    <span class="info-value"><%= user.getName() %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Email :</span>
                    <span class="info-value"><%= user.getEmail() %></span>
                </div>
            </div>
            
            <% if (user.getFichier() != null) {
                MapFile f = user.getFichier();
            %>
                <div class="info-block">
                    <h3>📎 Fichier associé à l'objet User</h3>
                    <div class="file-card">
                        <span class="file-icon"><%= f.getExtension() %></span>
                        <strong><%= f.getFileName() %></strong>
                        <div style="margin-top: 8px;">
                            <div class="info-row">
                                <span class="info-label">Taille :</span>
                                <span class="info-value"><%= f.getSizeFormatted() %></span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Extension :</span>
                                <span class="info-value"><%= f.getExtension() %></span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Chemin :</span>
                                <span class="info-value"><%= request.getAttribute("savedPath") %></span>
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
        <% } %>
        
        <a href="/app-test/sprint8" class="btn-back">⬅Nouvel upload</a>
        <a href="/app-test/" class="btn-back" style="background: #95a5a6; margin-left: 10px;">Accueil</a>
        
        <div class="footer"><strong>Framework MVC Artisanal</strong> &bull; Sprint 8 - Upload</div>
    </div>
</body>
</html>