┌─────────────────────────────────────────────────────────────┐
│                     web.xml                                │
│  <listener>                                                │
│    <listener-class>mg.itu.framework.FrameworkInitializer   │
│  </listener>                                               │
└─────────────────────────┬───────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────────┐
│              FrameworkInitializer                          │
│  implements ServletContextListener                         │
│                                                           │
│  contextInitialized() {                                   │
│    1. Scanner les contrôleurs                             │
│    2. Scanner les méthodes                                │
│    3. Construire le mapping                               │
│    4. Stocker dans ServletContext                         │
│  }                                                        │
└─────────────────────────┬───────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                FrontController                             │
│                                                           │
│  init() {                                                 │
│    1. Récupérer le mapping depuis ServletContext           │
│  }                                                        │
│                                                           │
│  processRequest() {                                       │
│    1. Utiliser le mapping pour router                     │
│  }                                                        │
└─────────────────────────────────────────────────────────────┘