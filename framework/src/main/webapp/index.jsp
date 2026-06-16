<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<% String error = (String) request.getAttribute("error"); %>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Connexion</title>
        <link rel="stylesheet" type="text/css" href="assets/css/style.css" />
        <link rel="stylesheet" type="text/css" href="assets/css/login.css" />
        <link rel="stylesheet" type="text/css" href="assets/bootstrap/bootstrap-icons/font/bootstrap-icons.css" />
        <link rel="stylesheet" type="text/css" href="assets/fonts/Camber/Camber.css" />
    </head>
    <style>
        span{
            color: red;
            font-size: 16px;
        }
        .text-success{
            color: green;
            font-size: 16px;
        }
    </style>
    <body>
        <div class="container login-container">
            <div class="login-div">
                <h1>Connexion</h1>
                <form id="login" class="login-form" action="login" method="POST">
                    <input type="text" id="login" name="login" placeholder="Entez votre nom d'utilisateur" />
                    <input type="password" id="motdepasse" name="motdepasse" placeholder="Entrez votre mot de passe" />
                    <input type="submit" value="Se connecter" />
                    <a href="gestion">Voir la liste des plats</a>
                    <div id="message">
                    <% if(error=="1") { %> 
                        <span>Email ou mot de passe incorrect</span>
                     <% } else { %>
                        <span class="text-success">Connexion reussi !</span>
                      <% } %>  
                    </div>
                </form>
            </div>
        </div>
    </body>
    <script src="../assets/bootstrap/js/bootstrap.bundle.js"></script>
    <script src="../assets/scripts/script-login.js"></script>
</html>