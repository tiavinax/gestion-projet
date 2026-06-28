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