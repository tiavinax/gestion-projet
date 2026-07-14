package mg.itu.framework;

import java.lang.reflect.*;
import java.util.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class FrameworkInitializer implements ServletContextListener {

    private final List<Class<?>> controllers = new ArrayList<>();
    private final Map<UrlMethod, MethodInfo> urlMapping = new HashMap<>();
    private final List<String> mappingErrors = new ArrayList<>();
    private boolean hasMappingConflicts = false;
    private ApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            ServletContext context = event.getServletContext();
            
            // 1. Créer le conteneur Spring
            applicationContext = new ApplicationContext();
            
            // 2. Scanner les composants
            List<Class<?>> componentClasses = ClassScanner.scanComponents(context);
            
            // 3. Trier les classes pour l'instanciation (dépendances d'abord)
            List<Class<?>> sortedClasses = sortByDependencies(componentClasses);
            
            // 4. Instancier les beans
            for (Class<?> clazz : sortedClasses) {
                Object instance = instantiateBean(clazz);
                applicationContext.registerBean(clazz, instance);
            }
            
            // 5. Injecter les dépendances
            for (Class<?> clazz : sortedClasses) {
                Object instance = applicationContext.getBean(clazz);
                injectDependencies(instance);
            }
            
            // 6. Récupérer les contrôleurs (pour le mapping)
            for (Class<?> clazz : componentClasses) {
                if (clazz.isAnnotationPresent(Controller.class)) {
                    controllers.add(clazz);
                }
            }
            
            // 7. Scanner les méthodes @RequestMapping
            scanMethods();
            
            // 8. Stocker dans ServletContext
            context.setAttribute("applicationContext", applicationContext);
            context.setAttribute("controllers", controllers);
            context.setAttribute("urlMapping", urlMapping);
            context.setAttribute("mappingErrors", mappingErrors);
            context.setAttribute("hasMappingConflicts", hasMappingConflicts);
            
            // 9. Afficher les beans
            applicationContext.showBeans();
            
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'initialisation du framework", e);
        }
    }

    private List<Class<?>> sortByDependencies(List<Class<?>> classes) {
        // Ordre simple: Repository → Service → Controller
        List<Class<?>> result = new ArrayList<>();
        List<Class<?>> repositories = new ArrayList<>();
        List<Class<?>> services = new ArrayList<>();
        List<Class<?>> controllers = new ArrayList<>();
        List<Class<?>> others = new ArrayList<>();

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Repository.class)) {
                repositories.add(clazz);
            } else if (clazz.isAnnotationPresent(Service.class)) {
                services.add(clazz);
            } else if (clazz.isAnnotationPresent(Controller.class)) {
                controllers.add(clazz);
            } else {
                others.add(clazz);
            }
        }

        result.addAll(repositories);
        result.addAll(services);
        result.addAll(controllers);
        result.addAll(others);
        
        return result;
    }

    private Object instantiateBean(Class<?> clazz) throws Exception {
        // Trouver le constructeur avec le plus de paramètres
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<?> selectedConstructor = null;
        int maxParams = -1;

        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() > maxParams) {
                maxParams = constructor.getParameterCount();
                selectedConstructor = constructor;
            }
        }

        if (selectedConstructor == null) {
            return clazz.getDeclaredConstructor().newInstance();
        }

        // Résoudre les dépendances du constructeur
        Class<?>[] paramTypes = selectedConstructor.getParameterTypes();
        Object[] args = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            args[i] = applicationContext.getBean(paramTypes[i]);
            if (args[i] == null) {
                // Si le bean n'existe pas encore, l'instancier récursivement
                Object dep = instantiateBean(paramTypes[i]);
                applicationContext.registerBean(paramTypes[i], dep);
                args[i] = dep;
            }
        }

        selectedConstructor.setAccessible(true);
        return selectedConstructor.newInstance(args);
    }

    private void injectDependencies(Object instance) throws Exception {
        Class<?> clazz = instance.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Object dependency = applicationContext.getBean(field.getType());
                if (dependency != null) {
                    field.set(instance, dependency);
                }
            }
        }
    }

    private void scanMethods() {
        urlMapping.clear();
        mappingErrors.clear();
        hasMappingConflicts = false;

        for (Class<?> controllerClass : controllers) {
            for (Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    String url = normalizeUrl(annotation.value());
                    String httpMethod = annotation.method().trim().toUpperCase();

                    UrlMethod key = new UrlMethod(url, httpMethod);
                    MethodInfo info = new MethodInfo(controllerClass, method);

                    if (urlMapping.containsKey(key)) {
                        MethodInfo existing = urlMapping.get(key);
                        mappingErrors.add(String.format(
                            "CONFLIT: %s (%s) -> %s.%s() et %s.%s()",
                            url, httpMethod,
                            existing.getControllerName(), existing.getMethodName(),
                            controllerClass.getSimpleName(), method.getName()
                        ));
                        hasMappingConflicts = true;
                    }
                    urlMapping.put(key, info);
                }
            }
        }
    }

    private String normalizeUrl(String url) {
        url = url.trim();
        return url.startsWith("/") ? url : "/" + url;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Nettoyage
    }
}