# SPRINT 0 - Architecture et Communication

## Objectif
- [x] Créer 2 répertoires : framework et app-test
- [x] Compiler le framework en .jar
- [x] Intégrer le .jar dans l'app-test
- [x] Communication entre framework et app-test

## Tâches réalisées

### Framework (REPERTOIRE 2)
- [x] Structure du projet (src/main/java, src/main/webapp)
- [x] Classe TestCommunication pour tester la communication
- [x] Script build.sh pour compiler en .jar
- [x] Génération du fichier framework-1.0.jar
- [x] Fichier README.md

### App Test (REPERTOIRE 1)
- [x] Structure du projet (src/main/java, src/main/webapp)
- [x] Intégration du framework-1.0.jar dans lib/
- [x] Servlet TestServlet qui utilise le framework
- [x] Script deploy.sh pour déployer vers Tomcat
- [x] Page web qui affiche la communication

### Communication
- [x] Le framework est compilé en .jar
- [x] L'app-test importe le .jar
- [x] La servlet utilise la classe du framework
- [x] Affichage du message de confirmation
- [x] Déploiement sur Tomcat fonctionnel

### Scripts
- [x] build.sh (framework) → génère framework-1.0.jar
- [x] install.sh → copie le .jar vers app-test/lib/
- [x] deploy.sh (app-test) → compile et déploie le .war

### Résultat final
-  Le framework est compilé et utilisable
-  L'app-test communique avec le framework
-  La page web affiche la confirmation
-  Déploiement sur Tomcat réussi
-  Pull Request prête pour la soumission

# SPRINT 1 - Validation

## Ce qui est fait
- [x] Annotation @Controller créée
- [x] FrontController scanne toutes les classes
- [x] Liste des contrôleurs stockée
- [x] Liste affichée dans les logs
- [x] Liste transmise à la JSP via request.setAttribute()
- [x] JSP liste_controller.jsp créée
- [x] Affichage avec style CSS
- [x] Déploiement vers Tomcat
- [x] Test en production

## Résultat final
- [x] Page web affiche la liste des @Controller
- [x] Liste contient les classes du framework ET de l'app-test
- [x] Framework et app-test communiquent correctement


# SPRINT 3 - Gestion des méthodes HTTP (GET/POST)

## Objectif
- Ajouter la gestion des méthodes HTTP (GET, POST) dans le mapping
- Utiliser une classe UrlMethod comme clé du Map

## Tâches
- [ok] 1. Créer la classe UrlMethod avec equals() et hashCode()
- [ok] 2. Modifier l'annotation @RequestMapping avec attribut "method"
- [ok] 3. Modifier MethodInfo (enlever url, garder seulement controller + method)
- [ok] 4. Modifier le scan pour utiliser UrlMethod comme clé
- [ok] 5. Modifier le FrontController pour la recherche
- [ok] 6. Mettre à jour l'affichage des mappings
- [ok] 7. Gérer les erreurs (méthode non supportée)
- [ok] 8. Tester avec GET et POST


# SPRINT 4 - Utilisation d'un ContextListener

## Objectif
Déplacer l'initialisation du framework du FrontController vers un ServletContextListener

## Tâches
- [ok] 1. Créer la classe FrameworkInitializer implements ServletContextListener
- [ok] 2. Déplacer la logique de scan dans contextInitialized()
- [ok] 3. Stocker les données dans ServletContext (urlMapping, controllers, errors)
- [ok] 4. Modifier FrontController pour récupérer les données depuis ServletContext
- [ok] 5. Supprimer scanMethods() de FrontController
- [ok] 6. Déclarer le listener dans web.xml
- [ok] 7. Tester le bon fonctionnement

## Structure
- Initialisation : FrameworkInitializer (ContextListener)
- Routing : FrontController (Servlet)
- Stockage : ServletContext

# SPRINT 5 - ModelView

## Objectif
Créer une classe ModelView pour encapsuler vue et données

## Tâches
- [ok] 1. Créer la classe ModelView
- [ok] 2. Modifier FrontController pour accepter ModelView
- [ok] 3. Gérer la résolution de vue (concaténation)
- [ok] 4. Adapter les contrôleurs de test
- [ok] 5. Tester avec GET et POST

## Structure
- ModelView : { viewName: String, data: Map<String, Object> }
- Résolution : viewName → /WEB-INF/views/viewName.jsp

# SPRINT 5b - Repository et Spring-Container

## Objectif
Créer un mini conteneur Spring qui gère l'instanciation et l'injection des dépendances

## Tâches
- [ok] 1. Créer l'annotation @Component
- [ok] 2. Créer l'annotation @Autowired
- [ok] 3. Créer la classe ApplicationContext
- [ok] 4. Scanner les classes @Component (y compris @Controller, @Repository, @Service)
- [ok] 5. Instancier les beans dans le bon ordre
- [ok] 6. Injecter les dépendances via @Autowired
- [ok] 7. Modifier FrameworkInitializer pour initialiser le conteneur
- [ok] 8. Ajouter ApplicationContext comme paramètre des contrôleurs
- [ok] 9. Créer des Repository de test
- [ok] 10. Afficher les beans dans une page de test

## Structure
- @Component → classe marquée comme bean
- @Autowired → injection de dépendance
- ApplicationContext → conteneur des beans
- Repository → accès aux données
- Service → logique métier
- Controller → point d'entrée MVC

# SPRINT 6 - Web Service (JSON API)

## Tâches
- [ok] 1. Ajouter Gson au classpath
- [ok] 2. Créer l'annotation @WebAPI
- [ok] 3. Modifier MethodInfo (pas nécessaire, on utilise l'annotation directement)
- [ok] 4. Modifier FrontController.executeMethod() pour détecter @WebAPI
- [ok] 5. Sérialiser avec Gson + écrire avec PrintWriter
- [ok] 6. Créer un ApiController de test
- [ok] 7. Tester avec curl / navigateur
- [ok] 8. Ajouter une page sprint6.jsp pour la documentation

# SPRINT 7 - Binding par attributs

## Objectif
Remplir automatiquement les paramètres de la méthode à partir des
paramètres de la requête HTTP (formulaire).

## Tâches
- [ok] 1. Modifier invokeMethod() pour gérer les paramètres simples
- [ok] 2. Détecter : HttpServletRequest / HttpServletResponse / primitif / String
- [ok] 3. Récupérer request.getParameter(paramName)
- [ok] 4. Convertir la valeur selon le type (String, int, double, boolean)
- [ok] 5. Passer les arguments dans le bon ordre
- [ok] 6. Créer un formulaire de test (ajout utilisateur)
- [ok] 7. Tester avec POST

## Structure
- Détection par type : request / response / String / int / double / boolean
- Conversion : String → primitif si nécessaire
- Ordre : respecter l'ordre des paramètres de la méthode

# SPRINT 7b - Binding Object

## Objectif
Remplir automatiquement un objet (User, Produit, etc.) à partir
des paramètres de la requête HTTP.

# SPRINT 7b - Binding Object

## Tâches
- [ok] 1. Ajouter isCustomObject() dans FrontController
- [ok] 2. Ajouter bindObject() pour remplir l'objet
- [ok] 3. Modifier invokeMethod() pour détecter les objets
- [ok] 4. Créer un modèle Produit (ou utiliser User existant)
- [ok] 5. Créer un formulaire de test
- [ok] 6. Tester avec POST

## Structure
- Détection : isCustomObject(type)
- Instanciation : type.newInstance()
- Binding : field.set(obj, convertedValue)
- Conversion : String → type du champ

# SPRINT 8 - Upload de fichiers

## Tâches
- [ok] 1. Créer la classe MapFile
- [ok] 2. Modifier web.xml (multipart-config)
- [ok] 3. Modifier invokeMethod() + bindObject() pour MapFile
- [ok] 4. Ajouter extractMapFile() dans FrontController
- [ok] 5. Créer le dossier uploads/
- [ok] 6. Créer UploadController
- [ok] 7. Créer sprint8_form.jsp
- [ok] 8. Créer sprint8_result.jsp
- [ok] 9. Bouton dans index.jsp

# SPRINT 9 - Gestion des sessions (MySession)

## Tâches
- [ ] 1. Créer la classe MySession (get, set + autres)
- [ ] 2. Modifier FrontController (injecter MySession)
- [ ] 3. Créer un SessionController pour tester
- [ ] 4. Créer la page sprint9.jsp