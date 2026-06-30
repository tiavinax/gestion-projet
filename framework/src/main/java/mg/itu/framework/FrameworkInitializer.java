package mg.itu.framework;

import java.lang.reflect.Method;
import java.util.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class FrameworkInitializer implements ServletContextListener {

    private final List<Class<?>> controllers = new ArrayList<>();
    private final Map<UrlMethod, MethodInfo> urlMapping = new HashMap<>();
    private final List<String> mappingErrors = new ArrayList<>();
    private boolean hasMappingConflicts = false;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            ServletContext context = event.getServletContext();
            
            // Scanne des controleurs
            List<Class<?>> scannedControllers = ClassScanner.scanControllers(context);
            controllers.addAll(scannedControllers);
            
            // Scanne des methodes
            scanMethods();
            
            // Stocker dans ServletContext
            context.setAttribute("controllers", controllers);
            context.setAttribute("urlMapping", urlMapping);
            context.setAttribute("mappingErrors", mappingErrors);
            context.setAttribute("hasMappingConflicts", hasMappingConflicts);
            
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'initialisation du framework", e);
        }
    }

    private void scanMethods() {
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
        // Nettoyage si nécessaire
    }
}