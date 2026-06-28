<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, mg.itu.framework.Controller" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Contrôleurs - Sprint 1</title>
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
        .info-bar strong {
            color: #2c3e50;
        }
        .controller-list {
            list-style: none;
        }
        .controller-item {
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
        .controller-item:hover {
            background: #eef2f7;
            transform: translateX(5px);
        }
        .controller-name {
            font-weight: 600;
            color: #2c3e50;
            font-size: 16px;
        }
        .controller-class {
            color: #7f8c8d;
            font-size: 13px;
            font-family: 'Courier New', monospace;
        }
        .controller-number {
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
        .empty-state {
            text-align: center;
            padding: 50px 20px;
            color: #7f8c8d;
        }
        .empty-state .icon {
            font-size: 48px;
            margin-bottom: 15px;
        }
        .footer {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #ecf0f1;
            font-size: 13px;
            color: #95a5a6;
            text-align: center;
        }
        .time {
            color: #95a5a6;
            font-size: 13px;
        }
        .status-ok {
            color: #27ae60;
            font-weight: 600;
        }
        .status-ko {
            color: #e74c3c;
            font-weight: 600;
        }
        .spring-badge {
            display: inline-block;
            background: #27ae60;
            color: white;
            padding: 2px 10px;
            border-radius: 12px;
            font-size: 11px;
            font-weight: 600;
            margin-left: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            📋 Liste des Contrôleurs 
            <span class="badge">Sprint 1</span>
        </h1>
        
        <div class="info-bar">
            <div>
                <span>📦 Nombre total : <strong>${totalControllers}</strong> contrôleur(s)</span>
                <span class="spring-badge">@Controller</span>
            </div>
            <div>
                <span class="time">🕐 ${serverTime}</span>
            </div>
        </div>
        
        <c:choose>
            <c:when test="${empty controllers}">
                <div class="empty-state">
                    <div class="icon">🔍</div>
                    <h3>Aucun contrôleur trouvé</h3>
                    <p>Ajoutez l'annotation <code>@Controller</code> à vos classes.</p>
                </div>
            </c:when>
            <c:otherwise>
                <ul class="controller-list">
                    <c:forEach var="controller" items="${controllers}" varStatus="status">
                        <li class="controller-item">
                            <div style="display: flex; align-items: center; gap: 15px; flex: 1;">
                                <span class="controller-number">${status.index + 1}</span>
                                <div>
                                    <div class="controller-name">
                                        ${controller.simpleName}
                                        <c:if test="${controller.annotation != null}">
                                            <span style="font-size: 12px; color: #3498db; margin-left: 8px;">
                                                (${controller.getAnnotation(mg.itu.framework.Controller.class).value()})
                                            </span>
                                        </c:if>
                                    </div>
                                    <div class="controller-class">${controller.name}</div>
                                </div>
                            </div>
                            <span style="font-size: 12px; color: #27ae60;">✅ chargé</span>
                        </li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
        
        <div class="footer">
            <strong>Framework MVC Artisanal</strong> &bull; Scanner automatique des @Controller &bull; 
            ${totalControllers} contrôleur(s) chargé(s)
        </div>
    </div>
</body>
</html>