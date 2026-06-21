# Fonctionnaliter

======== 09/06/2026 ===============

SPRINT 0 (j'imagine que c'est "SPRINT 0")
Structure :

REPERTOIRE 2 - FRAMEWORK (la bibliothèque)

Contient les codes source d'un framework maison
Contient le web.xml avec le mapping des servlets
À pusher sur GitHub
Sortie : Un fichier .jar (librairie réutilisable)

REPERTOIRE 1 - APPLICATION WEB DE TEST

Utilise la librairie (le .jar) fournie par le framework
Met le .jar dans le dossier lib de l'application web
Se déploie sur Tomcat
Sert à tester le framework

**Le but :**
Créer un framework réutilisable (comme Spring MVC simplifié)
Le compiler en .jar pour le distribuer
Avoir une application de test séparée qui utilise ce framework
Valider que le framework fonctionne correctement via l'application de test

=========== 16/06/26 ========================
SPRINT 1: 

Concept a apprendre :
- Annotation(mg.itu.framework.Controller) : boug de code => List<class<framwork>>et List<class<framework + app-test>>  une fois deployer
- Chargement des class
- Demmarage de l'applicatin web (appele d'une methode au premiere appelle de FrontServletController dans void Init() ou ContextListener)

Objectif : scanner le projet qui contient l'annotation Controller et au premier chargement de l'app dans void Init() boucler 

=========== 19/06/16 ==========================
SPRINT 2:

- Creer class annotation pour associe une methode et un url (target = methode)
- Output : afficher dans une page :  url + controller + methode associer
    ex : /dept/new -> DeptController -> create
- Lever execption url non supporter
    - si non supporter afficher tous les url supporter
