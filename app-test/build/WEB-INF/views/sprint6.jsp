<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sprint 6 - Web Service</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f7fa; padding: 40px; }
        .container { max-width: 900px; margin: 0 auto; background: white; border-radius: 12px; padding: 40px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        h1 { color: #2c3e50; border-bottom: 3px solid #e74c3c; padding-bottom: 15px; margin-bottom: 20px; }
        .badge { display: inline-block; background: #e74c3c; color: white; padding: 4px 14px; border-radius: 20px; font-size: 14px; font-weight: 600; }
        .info-bar { background: #ecf0f1; padding: 15px 20px; border-radius: 8px; margin-bottom: 25px; display: flex; justify-content: space-between; flex-wrap: wrap; gap: 10px; }
        .api-card { background: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 15px; border-left: 4px solid #e74c3c; }
        .api-card h3 { color: #2c3e50; margin-bottom: 8px; font-family: monospace; }
        .api-card .method { display: inline-block; background: #27ae60; color: white; padding: 2px 10px; border-radius: 12px; font-size: 12px; font-weight: 600; margin-right: 10px; }
        .api-card .url { color: #3498db; font-family: monospace; font-weight: 600; }
        .api-card .desc { color: #7f8c8d; font-size: 14px; margin-top: 8px; }
        .api-card a { display: inline-block; background: #e74c3c; color: white; padding: 6px 16px; border-radius: 6px; text-decoration: none; font-size: 13px; margin-top: 10px; }
        .api-card a:hover { background: #c0392b; }
        .btn-back { display: inline-block; background: #3498db; color: white; padding: 10px 25px; border-radius: 6px; text-decoration: none; margin-top: 20px; }
        .btn-back:hover { background: #2980b9; }
        .footer { margin-top: 30px; padding-top: 20px; border-top: 1px solid #ecf0f1; text-align: center; color: #95a5a6; font-size: 13px; }
        .code-box { background: #2c3e50; color: #ecf0f1; padding: 15px; border-radius: 6px; font-family: monospace; font-size: 13px; margin-top: 10px; overflow-x: auto; }
        .code-box .annotation { color: #f39c12; }
        .code-box .keyword { color: #3498db; }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            🔌 <%= request.getAttribute("sprintName") %>
            <span class="badge">Sprint 6</span>
        </h1>
        <p style="color: #7f8c8d; margin-bottom: 20px;"><%= request.getAttribute("sprintDesc") %></p>
        
        <div class="info-bar">
            <span>📦 Format : <strong>JSON</strong></span>
            <span>🕐 <%= request.getAttribute("serverTime") %></span>
        </div>
        
        <h3 style="margin: 20px 0 15px; color: #2c3e50;">🌐 APIs disponibles</h3>
        
        <div class="api-card">
            <h3><span class="method">GET</span><span class="url">/api/users</span></h3>
            <p class="desc">Retourne la liste de tous les utilisateurs en JSON</p>
            <a href="/app-test/api/users" target="_blank">Tester →</a>
        </div>
        
        <div class="api-card">
            <h3><span class="method">GET</span><span class="url">/api/user?id=1</span></h3>
            <p class="desc">Retourne un utilisateur par son ID</p>
            <a href="/app-test/api/user?id=1" target="_blank">Tester (id=1) →</a>
        </div>
        
        <div class="api-card">
            <h3><span class="method">GET</span><span class="url">/api/hello</span></h3>
            <p class="desc">Message de bienvenue en JSON</p>
            <a href="/app-test/api/hello" target="_blank">Tester →</a>
        </div>
        
        <div class="api-card">
            <h3><span class="method">GET</span><span class="url">/api/status</span></h3>
            <p class="desc">État du framework en JSON</p>
            <a href="/app-test/api/status" target="_blank">Tester →</a>
        </div>
        
        <h3 style="margin: 25px 0 15px; color: #2c3e50;">📝 Exemple d'utilisation</h3>
        <div class="code-box">
<span class="annotation">@WebAPI</span>
<span class="annotation">@RequestMapping</span>(value = <span style="color: #e67e22;">"/api/users"</span>, method = <span style="color: #e67e22;">"GET"</span>)
<span class="keyword">public</span> List&lt;User&gt; getUsers() {
    <span class="keyword">return</span> userService.getAllUsers();
}
        </div>
        
        <a href="/app-test/" class="btn-back">🏠 Retour à l'accueil</a>
        <div class="footer"><strong>Framework MVC Artisanal</strong> &bull; Sprint 6 - WebAPI</div>
    </div>
</body>
</html>