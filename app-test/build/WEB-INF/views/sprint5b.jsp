<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*" %>
        <%@ page import="java.util.*, com.test.model.User" %>
            <% List<String> users = (List<String>) request.getAttribute("users"); %>
                    <!DOCTYPE html>
                    <html>

                    <head>
                        <meta charset="UTF-8">
                        <title>Sprint 5b</title>
                    </head>

                    <body>
                        <div class="container">
                            <h1>Repository</h1>
                            <div class="card">
                                <h3>👥 Utilisateurs (Repository → Service → Controller)</h3>
                                <ul>
                                    <% List<User> users = (List<User>) request.getAttribute("users");
                                            if (users != null) {
                                            for (User user : users) {
                                            %>
                                            <li>• <strong>
                                                    <%= user.getName() %>
                                                </strong> (ID: <%= user.getId() %>) — <%= user.getEmail() %>
                                            </li>
                                            <% } } %>
                                </ul>
                            </div>
                            <a href="/app-test/" class="btn-back">Retour à l'accueil</a>
                        </div>
                    </body>

                    </html>