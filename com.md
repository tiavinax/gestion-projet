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