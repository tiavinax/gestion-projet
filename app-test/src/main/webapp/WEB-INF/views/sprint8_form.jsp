<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sprint 8 - Upload de fichiers</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f7fa; padding: 40px; }
        .container { max-width: 700px; margin: 0 auto; background: white; border-radius: 12px; padding: 40px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        h1 { color: #2c3e50; border-bottom: 3px solid #16a085; padding-bottom: 15px; margin-bottom: 20px; }
        .badge { display: inline-block; background: #16a085; color: white; padding: 4px 14px; border-radius: 20px; font-size: 14px; font-weight: 600; }
        .section { background: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 25px; border-left: 4px solid #16a085; }
        .section h3 { color: #2c3e50; margin-bottom: 15px; font-size: 16px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: 600; color: #2c3e50; margin-bottom: 6px; font-size: 14px; }
        input[type="text"], input[type="email"] { width: 100%; padding: 10px 14px; border: 2px solid #e9ecef; border-radius: 6px; font-size: 14px; }
        input[type="text"]:focus, input[type="email"]:focus { outline: none; border-color: #16a085; }
        input[type="file"] { padding: 8px; border: 2px dashed #16a085; border-radius: 6px; background: white; width: 100%; cursor: pointer; }
        button { background: #16a085; color: white; padding: 12px 30px; border: none; border-radius: 6px; font-size: 15px; font-weight: 600; cursor: pointer; width: 100%; }
        button:hover { background: #138d75; }
        .btn-back { display: block; background: #95a5a6; color: white; padding: 10px 25px; border-radius: 6px; text-decoration: none; margin-top: 15px; text-align: center; }
        .btn-back:hover { background: #7f8c8d; }
        code { background: #f8f9fa; padding: 2px 8px; border-radius: 4px; font-size: 13px; color: #c0392b; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Upload de fichiers <span class="badge">Sprint 8</span></h1>
        
        <!-- Mode 1 : Upload simple -->
        <div class="section">
            <h3>Upload simple (MapFile)</h3>
            <form action="/app-test/sprint8/upload-single" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label>Nom</label>
                    <input type="text" name="nom" placeholder="Alice" required>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" placeholder="alice@mail.com" required>
                </div>
                <div class="form-group">
                    <label>Fichier</label>
                    <input type="file" name="fichier" required>
                </div>
                <button type="submit">Upload simple</button>
            </form>
            <p style="margin-top: 10px; font-size: 13px; color: #7f8c8d;">
                Signature : <code>uploadSingle(String nom, String email, MapFile fichier)</code>
            </p>
        </div>
        
        <!-- Mode 2 : Upload multiple -->
        <div class="section">
            <h3>Upload multiple (MapFile[])</h3>
            <form action="/app-test/sprint8/upload-multiple" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label>Fichiers (plusieurs)</label>
                    <input type="file" name="fichiers" multiple required>
                </div>
                <button type="submit">Upload multiple</button>
            </form>
            <p style="margin-top: 10px; font-size: 13px; color: #7f8c8d;">
                Signature : <code>uploadMultiple(MapFile[] fichiers)</code>
            </p>
        </div>
        
        <!-- Mode 3 : Upload via objet User -->
        <div class="section">
            <h3>Upload via objet User (MapFile dans objet)</h3>
            <form action="/app-test/sprint8/upload-object" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label>Nom (name)</label>
                    <input type="text" name="name" placeholder="Alice" required>
                </div>
                <div class="form-group">
                    <label>Email (email)</label>
                    <input type="email" name="email" placeholder="alice@mail.com" required>
                </div>
                <div class="form-group">
                    <label>Fichier (fichier)</label>
                    <input type="file" name="fichier" required>
                </div>
                <button type="submit">Upload via objet</button>
            </form>
            <p style="margin-top: 10px; font-size: 13px; color: #7f8c8d;">
                Signature : <code>uploadObject(User user)</code> — avec <code>User.fichier</code>
            </p>
        </div>
        
        <a href="/app-test/" class="btn-back">Accueil</a>
    </div>
</body>
</html>