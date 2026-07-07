<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, mg.itu.framework.UrlMethod, mg.itu.framework.MethodInfo" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Sprint 5 - ModelView</title>
</head>

<body>
    <div class="container">
        <div class="demo-box">
            <ul>
            <li><%= request.getAttribute("Nombre 12") %></li>
            <li><%= request.getAttribute("test") %></li>
            </ul>
        </div>
    </div></body>
</html>