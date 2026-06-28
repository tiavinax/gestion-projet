gestion_projet/
├── framework/           # REPERTOIRE 2
│   ├── build.sh        # Script adapté ci-dessus
│   ├── lib/
│   │   └── servlet-api.jar
│   ├── src/
│   │   └── main/
│   │       ├── java/    # Vos classes du framework
│   │       └── webapp/
│   │           ├── web.xml
│   │           └── ...
│   ├── build/           # Classes compilées (temporaire)
│   └── dist/            # JAR généré ici !
│       └── framework-1.0.jar
│
└── app-test/            # REPERTOIRE 1 (à créer)
    ├── lib/
    │   └── framework-1.0.jar  # Copié depuis framework/dist/
    ├── src/
    │   └── main/
    │       ├── java/     # Vos servlets de test
    │       └── webapp/
    │           ├── WEB-INF/
    │           │   └── web.xml
    │           └── *.jsp
    └── build.sh         # Script pour compiler le .war de test