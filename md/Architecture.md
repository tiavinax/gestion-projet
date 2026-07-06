FRAMEWORK (JAR)                    APP TEST (WAR)
    │                                    │
    │   ┌──────────────────────────┐     │
    │   │  Classes du framework    │     │
    │   │  - FrontController       │◄─── ┤ Importe le JAR
    │   │  - ModelView             │     │
    │   │  - Annotation @Controller│     │
    │   └──────────────────────────┘     │
    │                                    │
    └──────────────┬─────────────────────┘
                   │
                   ▼
            Communication OK ?
         (App test utilise le JAR)

    index.html (Page d'accueil)
    ├── Sprint 1 → /sprint1 (affiche les contrôleurs avec @Controller)
    ├── Sprint 2 → /sprint2 (affiche les mappings URL → méthode)
    ├── Sprint 3 → /sprint3 (affiche les mappings avec GET/POST)
    ├── Sprint 4 → /sprint4 (affiche les mappings + ModelView)
    └── Sprint 5 → /sprint5 (affiche les mappings avec ModelView)