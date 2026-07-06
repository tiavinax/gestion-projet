# Fonctionnaliter

======== 09/06/2026 ===============

SPRINT 0 
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

    m commit sprint2 : 
    - Création de l'annotation @RequestMapping"
    - Scanner les méthodes des contrôleurs"
    - Mapping URL → Contrôleur → Méthode"
    - Affichage des URLs supportées"
    - Gestion des erreurs 404 avec liste des URLs"

============ 23/06/26 ===========================
SPRINT 3a: Enrichissement de SPRINT 2
        - Gestion de GET/POST dans les mapping
        ex : 1. @RequestMapping("/test", "GET")
             2. @RequestMapping("/test", "GET")
        Map<url,MethodeInfo> 
        Methode 1 : Concatener l'url 
        Methode 2 : Builder un autre class : UrlMethode:- url
                                                        - methode il faut surcharger un methode equals pour l'attribue     methode
        C'est a dire l'annotation @RequestMapping doit prendre une deuxieme attribue methode
SPRINT 3b: 
        - il faut executer le methode appeler par l'url 

============ 30/06/26 ===========================
SPRINT 4: Utiliser une ContextListner une class Listner qui a pour role d'appeler void init() au chargment de l'application

============ 02/07/26 ===========================
SPRINT 5: Comment envoyer les donner vers une vue 
    Exemple : dans spring mvc :
    
    @GetMapping("/secretariat/profil/{id}")
    public String profil(@PathVariable Integer id, Model model) {
        model.addAttribute("eleve", eleveService.getProfil(id));
        model.addAttribute("pageTitle", "Profil de l'Élève");
        return "Secretaire/profil_eleve";
    }
    
    methode : class ModelView : String viewName, Map<String, Object> data
    ┌─────────────────────────────────────────────────────┐
    │                    ModelView                        │
    │  ┌─────────────┐  ┌─────────────────────────────┐   │
    │  │ viewName    │  │ Map<String, Object> data    │   │
    │  │ "mappings"  │  │  "message" → "Hello"        │   │
    │  └─────────────┘  │  "timestamp" → Date         │   │
    │                    │  "eleve" → Eleve           │  │
    │                    └─────────────────────────────┘  │
    └─────────────────────────────────────────────────────┘
    Il faut conctaner le /emp/list en /emp/list.jsp
