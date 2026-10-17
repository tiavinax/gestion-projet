<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un utilisateur</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f7fa; padding: 40px; }
        .container { max-width: 500px; margin: 0 auto; background: white; border-radius: 12px; padding: 40px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        h1 { color: #2c3e50; border-bottom: 3px solid #3498db; padding-bottom: 15px; margin-bottom: 25px; }
        .badge { display: inline-block; background: #3498db; color: white; padding: 4px 14px; border-radius: 20px; font-size: 14px; font-weight: 600; }
        .form-group { margin-bottom: 20px; }
        label { display: block; font-weight: 600; color: #2c3e50; margin-bottom: 8px; }
        input[type="text"], input[type="email"], input[type="number"] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.2s;
        }
        input:focus { outline: none; border-color: #3498db; }
        button {
            width: 100%;
            background: #27ae60;
            color: white;
            padding: 14px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
        }
        button:hover { background: #219a52; }
        .btn-back {
            display: inline-block;
            background: #3498db;
            color: white;
            padding: 10px 25px;
            border-radius: 6px;
            text-decoration: none;
            margin-top: 15px;
            text-align: center;
            width: 100%;
        }
        .btn-back:hover { background: #2980b9; }
        .info { background: #e8f4fd; padding: 12px 18px; border-radius: 6px; margin-bottom: 20px; font-size: 14px; color: #2c3e50; border-left: 4px solid #3498db; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Ajouter un utilisateur <span class="badge" style="text-align: center;">Sprint 7</span></h1>
        
        <div class="info">
            Le framework remplit automatiquement les paramètres de la méthode
            <code>save(String nom, String email, int age)</code>
        </div>
        
        <form action="/app-test/user/save-attr" method="POST">
            <div class="form-group">
                <label>Nom</label>
                <input type="text" name="nom" placeholder="Alice" required>
            </div>
            
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" placeholder="alice@mail.com" required>
            </div>
            
            <div class="form-group">
                <label>Âge</label>
                <input type="number" name="age" placeholder="25" min="0" required>
            </div>
            
            <button type="submit">Envoyer</button>
        </form>
        
        <a href="/app-test/" class="btn-back">Retour à l'accueil</a>
    </div>
</body>
</html>