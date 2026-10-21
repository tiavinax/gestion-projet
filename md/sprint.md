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
SPRINT 5: 
    a. Comment envoyer les donner vers une vue 
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

    sprint 5b : (Module JPA) : Modifier les donnees passer au vue avec les donnees venu du SGBD
        - creer un fichier db.config 
SPRINT 5b: Repository: Base de donner : findAll(), getById(id),etc - Utiliser un seul [Spring-Container] charger au demarrage de l'application avec Listner mais sans jamais creer le listner mais declarer dans le web.xml (listner + config-spring) (FrameworkInitializer.java) - methode : getBeans pour le context dans le Controller le Spring l'instanciation du controller ne change pas mais le controller va prendre un arguments (ApplicaitonContext appCt) et verifier lors de l'invocation du methode

    ┌─────────────────────────────────────────────────────────────┐
    │                    ApplicationContext                       │
    │  (Spring-Container)                                         │
    │  ┌───────────────────────────────────────────────────────┐  │
    │  │  Map<Class<?>, Object> beans                          │  │
    │  │  ┌─────────────────────────────────────────────────┐  │  │
    │  │  │  UserController → instance                      │  │  │
    │  │  │  ProduitController → instance                   │  │  │
    │  │  │  UserService → instance                         │  │  │
    │  │  │  ProduitService → instance                      │  │  │
    │  │  │  UserRepository → instance                      │  │  │
    │  │  │  ProduitRepository → instance                   │  │  │
    │  │  └─────────────────────────────────────────────────┘  │  │
    │  └───────────────────────────────────────────────────────┘  │
    │                                                             │
    │  Méthodes :                                                 │
    │  - getBean(Class<?>) → retourne l'instance                  │
    │  - getBeans() → retourne toutes les instances               │
    └─────────────────────────────────────────────────────────────┘
    
SPRINT 6 : Le framework peut renvoyer un Json (Web service(API))
On peut avoir un methode dans le controlleur qui ne retourne pas un vue mais juste un Json

on doit ajouter un autre annotation qu'on va mettre sur la methode qui va retourner le Json (@WebAPI) 
Si l'annotation est present on retourne json sinon RequestDipatcher.dispat();
## Methode : 
Cote test : la methode retourne un Objet 
cote Framework : on va invoke la methode et on doit formater l'object en json avec un library et c'est cette string 
qu'on va mettre dans printWriter();

Sprint final : Rest/upload/session/autorisation

SPRINT 7 : Binding 
Le framework doit automatiquement binder (lier) les paramètres du formulaire HTML aux paramètres de la méthode.

Après Sprint 7 :

```java
@RequestMapping(value = "/user/save", method = "POST")
public ModelView save(User user) {  // ✅ Binding automatique
    // Le framework a rempli user.nom, user.email, etc.
    userService.save(user);
    return new ModelView("user_list");
}
```
```java
@RequestMapping(value = "/user/add", method = "GET")
public ModelView add() {  // ✅ Aucun paramètre
    return new ModelView("user_form");
}
```
📚 Principe du Binding (Object)
Schéma
text
┌─────────────────────────────────────────────────────────────┐
│                    Formulaire HTML                          │
│                                                             │
│  <form action="/user/save" method="POST">                  │
│    <input name="nom" value="Alice">                        │
│    <input name="email" value="alice@mail.com">             │
│    <button>Envoyer</button>                                │
│  </form>                                                    │
└─────────────────────────┬───────────────────────────────────┘
                          │
                          │ POST /user/save
                          │ nom=Alice&email=alice@mail.com
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                    FrontController                          │
│                                                             │
│  1. Trouve le MethodInfo pour /user/save POST              │
│  2. Récupère la signature de la méthode : save(User)       │
│  3. Détecte le type du paramètre : User                    │
│  4. Instancie User                                         │
│  5. Pour chaque champ de User :                            │
│     - Cherche request.getParameter(fieldName)              │
│     - Convertit la valeur au bon type                      │
│     - setField(user, valeur)                               │
│  6. Appelle la méthode : save(user)                        │
└─────────────────────────────────────────────────────────────┘
Règles de binding
Formulaire HTML	Classe Java	Résultat
name="nom"	String nom	user.setNom("Alice")
name="age"	int age	user.setAge(25)
name="email"	String email	user.setEmail("...")


Binding par attribus

Formulaire HTML
```html
  <input name="nom" value="Alice">
  <input name="email" value="alice@mail.com">
  <input name="age" value="25">
```
        ↓
POST /user/save
nom=Alice&email=alice@mail.com&age=25
        ↓
FrontController analyse la signature : save(String nom, String email, int age)
        ↓
Pour chaque paramètre :
  - nom   → request.getParameter("nom")   → "Alice"   → String
  - email → request.getParameter("email") → "..."     → String
  - age   → request.getParameter("age")   → "25"      → int (parseInt)
        ↓
method.invoke(controller, "Alice", "alice@mail.com", 25)

SPRINT 7b: Binding Object 
Instance Object disponible 

┌─────────────────────────────────────────────────────────────┐
│                    Formulaire HTML                          │
│                                                             │
│  <input name="nom"   value="Alice">                         │
│  <input name="email" value="alice@mail.com">                │
│  <input name="age"   value="25">                            │
└─────────────────────────┬───────────────────────────────────┘
                          │
                          │ POST /user/save
                          │ nom=Alice&email=alice@mail.com&age=25
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                    FrontController                          │
│                                                             │
│  1. Détecte : save(User user)                               │
│  2. Type du paramètre : User (custom object)                │
│  3. Instancie : new User()                                  │
│  4. Pour chaque champ de User :                             │
│     - field "nom"    → request.getParameter("nom")          │
│     - field "email"  → request.getParameter("email")        │
│     - field "age"    → request.getParameter("age")          │
│  5. Convertit et assigne via setters                        │
│  6. Passe l'objet User à la méthode                         │
└─────────────────────────┬───────────────────────────────────┘
                          ▼
                    save(User user)

SPRINT 8 : Formulaire avec fichier

```html
<form method="POST" enctype="multipart/form-data">   ← ATTENTION
    <input name="name">
    <input name="email">
    <input type="file" name="fichier">               ← NOUVEAU
</form>
```

→ Content-Type : multipart/form-data
→ request.getParameter() NE FONCTIONNE PAS pour les fichiers
→ Il faut utiliser request.getPart(name) ou request.getParts()

La classe MapFile
```text
┌─────────────────────────────────────────────┐
│                 MapFile                     │
├─────────────────────────────────────────────┤
│  - fileName   : String   ← "document.pdf"  │
│  - extension  : String   ← "pdf"           │
│  - contenu    : byte[]   ← [0x25, 0x50...] │
├─────────────────────────────────────────────┤
│  + getters / setters                        │
└─────────────────────────────────────────────┘
fileName : nom complet du fichier
extension : extension (pdf, jpg, png, etc.)
contenu : les bytes bruts du fichier
```

🔄 Flux complet



                Formulaire HTML                          
```html                                                      
  <form method="POST" enctype="multipart/form-data">        
    <input name="name">                                     
    <input name="email">                                    
    <input type="file" name="fichier">                      
  </form>                                                   
```                                                         
───────────────────────┬────────────────────────────────────
                       │ POST (multipart/form-data)
                       │ name=Alice&email=...&fichier=[BINARY]
                       ▼
┌──────────────────────────────────────────────────────────── ┐
│                   FrontController                           │
│  1. Détecte : save(User user)                               │
│  2. Instancie : new User()                                  │
│  3. Pour chaque champ de User :                             │
│     - "name"    → request.getParameter("name")              │
│     - "email"   → request.getParameter("email")             │
│     - "fichier" → request.getPart("fichier")  ← NOUVEAU     │
│                   → Créer MapFile                           │
│                   → setFileName(part.getSubmittedFileName())│
│                   → setExtension(...)                       │
│                   → setContenu(part.getInputStream()...)    │
│  4. user.setFichier(mapFile)                                │
└───────────────────────┬──────────────────────────────────── ┘
                        ▼
                    save(User user)  ← user.fichier est rempli

SPRINT 9: GESTION DES SESSION (HttpSession)

Framework : Class MySession (get(String key , Class<T> type)/set(string key, Object value))
app-test : import mg.itu.framework.MySession;
@UrlMapping 
public ModelView sprint8(MySession session) {
        ModelView mv = new ModelView("sprint5b");
        List<User> users = session.get("users",User.class);
        mv.addAttribute("users", users);
    return mv;
}

Placer dans un contexte le map
 l'instance du class pour avoir un instance par client mais pas par appel 