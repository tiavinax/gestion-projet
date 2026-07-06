<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Framework MVC - Tests des Sprints</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f0f2f5;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            max-width: 800px;
            width: 100%;
            background: white;
            border-radius: 16px;
            padding: 50px 40px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
            text-align: center;
        }

        h1 {
            color: #2c3e50;
            font-size: 32px;
            margin-bottom: 8px;
        }

        .subtitle {
            color: #7f8c8d;
            font-size: 16px;
            margin-bottom: 35px;
        }

        .badge {
            display: inline-block;
            background: #3498db;
            color: white;
            padding: 4px 14px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: 600;
            margin-bottom: 15px;
        }

        .sprint-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 16px;
            margin: 30px 0 25px;
        }

        .sprint-btn {
            display: block;
            padding: 20px 10px;
            background: #f8f9fa;
            border-radius: 12px;
            text-decoration: none;
            color: #2c3e50;
            font-weight: 600;
            font-size: 16px;
            border: 2px solid #e9ecef;
            transition: all 0.2s;
        }

        .sprint-btn:hover {
            background: #eef2f7;
            border-color: #3498db;
            transform: translateY(-3px);
            box-shadow: 0 4px 12px rgba(52, 152, 219, 0.15);
        }

        .sprint-btn .num {
            display: block;
            font-size: 28px;
            margin-bottom: 6px;
        }

        .sprint-btn .label {
            font-size: 14px;
            font-weight: 400;
            color: #7f8c8d;
        }

        .sprint-btn.active {
            border-color: #27ae60;
            background: #eafaf1;
        }

        .footer {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #ecf0f1;
            font-size: 13px;
            color: #95a5a6;
        }

        .status {
            color: #27ae60;
            font-weight: 500;
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="badge">Framework MVC Artisanal</div>
        <h1> Tests des Sprints</h1>
        <p class="subtitle">Selectionnez un sprint pour voir le resultat</p>

        <div class="sprint-grid">
            <a href="/app-test/sprint1" class="sprint-btn">
                <span class="num">1</span>
                Scanner @Controller
                <span class="label">Liste des controleurs</span>
            </a>
            <a href="/app-test/sprint2" class="sprint-btn">
                <span class="num">2</span>
                @RequestMapping
                <span class="label">Mapping URL → Methode</span>
            </a>
            <a href="/app-test/sprint3" class="sprint-btn">
                <span class="num">3</span>
                GET / POST
                <span class="label">Methodes HTTP</span>
            </a>
            <a href="/app-test/sprint4" class="sprint-btn">
                <span class="num">4</span>
                ContextListener
                <span class="label">Initialisation</span>
            </a>
            <a href="/app-test/sprint5" class="sprint-btn">
                <span class="num">5</span>
                ModelView
                <span class="label">Vue + Donnees</span>
            </a>
        </div>

        <p class="status">Framework operationnel — Tous les sprints sont disponibles</p>
        <div class="footer">
            SPRINT 5 — Envoi de donnees vers une vue avec ModelView
        </div>
    </div>
</body>

</html>