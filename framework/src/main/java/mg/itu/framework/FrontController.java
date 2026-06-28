package mg.itu.framework;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FrontController extends HttpServlet {
    
    private List<Class<?>> controllers = new ArrayList<>();
    private Map<String, MethodInfo> urlMapping = new HashMap<>();
    
    @Override
    public void init() throws ServletException {
        System.out.println("========================================");
        System.out.println("FRAMEWORK MVC - SPRINT 2");
        System.out.println("========================================");
        
        try {
            scanControllers();
            scanMethods();
            displayMappings();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors du scan", e);
        }
    }
    
    private void scanControllers() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
        // 1. Scanner WEB-INF/classes (app-test)
        URL classesUrl = classLoader.getResource("");
        if (classesUrl != null) {
            String filePath = URLDecoder.decode(classesUrl.getFile(), "UTF-8");
            System.out.println("📁 Scan: " + filePath);
            File classesDir = new File(filePath);
            if (classesDir.exists() && classesDir.isDirectory()) {
                scanDirectory(classLoader, classesDir, "");
            }
        }
        
        // 2. Scanner le JAR du framework
        String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if (jarPath.endsWith(".jar")) {
            System.out.println("Scan JAR: " + jarPath);
            scanJarFile(jarPath);
        }
        
        // 3. Scanner les JAR dans WEB-INF/lib
        File libDir = new File(getServletContext().getRealPath("/WEB-INF/lib"));
        if (libDir.exists() && libDir.isDirectory()) {
            for (File jarFile : libDir.listFiles()) {
                if (jarFile.getName().endsWith(".jar") && !jarFile.getName().equals("servlet-api.jar")) {
                    System.out.println("Scan JAR: " + jarFile.getName());
                    scanJarFile(jarFile.getAbsolutePath());
                }
            }
        }
    }
    
    private void scanDirectory(ClassLoader classLoader, File directory, String packageName) {
        File[] files = directory.listFiles();
        if (files == null) return;
        
        for (File file : files) {
            if (file.isDirectory()) {
                String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                scanDirectory(classLoader, file, newPackage);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                // Ignorer les classes avec "main.java" dans le nom (problème de package)
                if (className.contains("main.java")) {
                    System.out.println("Ignoré (package incorrect): " + className);
                    continue;
                }
                try {
                    Class<?> clazz = Class.forName(className, false, classLoader);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        controllers.add(clazz);
                        System.out.println("✅ Contrôleur trouvé: " + clazz.getName());
                    }
                } catch (ClassNotFoundException e) {
                    // Ignorer
                }
            }
        }
    }
    
    private void scanJarFile(String jarPath) throws Exception {
        try (java.util.jar.JarFile jarFile = new java.util.jar.JarFile(jarPath)) {
            Enumeration<java.util.jar.JarEntry> entries = jarFile.entries();
            
            while (entries.hasMoreElements()) {
                java.util.jar.JarEntry entry = entries.nextElement();
                String name = entry.getName();
                
                if (name.endsWith(".class") && !name.contains("module-info")) {
                    String className = name.replace("/", ".").replace(".class", "");
                    // Ignorer les classes avec "main.java" dans le nom
                    if (className.contains("main.java")) {
                        continue;
                    }
                    try {
                        Class<?> clazz = Class.forName(className, false, Thread.currentThread().getContextClassLoader());
                        if (clazz.isAnnotationPresent(Controller.class)) {
                            controllers.add(clazz);
                            System.out.println("✅ Contrôleur trouvé (JAR): " + clazz.getName());
                        }
                    } catch (ClassNotFoundException e) {
                        // Ignorer
                    }
                }
            }
        }
    }
    
    private void scanMethods() {
        System.out.println("========================================");
        System.out.println("Scan des méthodes @RequestMapping");
        System.out.println("========================================");
        
        for (Class<?> controllerClass : controllers) {
            Method[] methods = controllerClass.getDeclaredMethods();
            
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    String url = annotation.value().trim();
                    if (!url.startsWith("/")) {
                        url = "/" + url;
                    }
                    
                    MethodInfo info = new MethodInfo(controllerClass, method, url);
                    urlMapping.put(url, info);
                    
                    System.out.println("Mapping: " + url + " → " + 
                                     controllerClass.getSimpleName() + "." + method.getName() + "()");
                }
            }
        }
        
        System.out.println("Total mappings: " + urlMapping.size());
        System.out.println("========================================");
    }
    
    private void displayMappings() {
        System.out.println("========================================");
        System.out.println("URLs supportées");
        System.out.println("========================================");
        
        if (urlMapping.isEmpty()) {
            System.out.println("Aucun mapping");
        } else {
            int i = 1;
            for (Map.Entry<String, MethodInfo> entry : urlMapping.entrySet()) {
                MethodInfo info = entry.getValue();
                System.out.println((i++) + ". " + entry.getKey() + " → " + 
                                 info.getControllerName() + "." + info.getMethodName() + "()");
            }
        }
        System.out.println("Total: " + urlMapping.size());
        System.out.println("========================================");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        System.out.println("🔍 Requête: " + path);
        
        // Cas spécial : page d'accueil → afficher TOUS les mappings
        if ("/".equals(path) || "".equals(path) || path == null) {
            request.setAttribute("urlMapping", urlMapping);
            request.setAttribute("totalUrls", urlMapping.size());
            request.setAttribute("totalControllers", controllers.size());
            request.setAttribute("serverTime", new java.util.Date());
            request.setAttribute("isError", false);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/mappings.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Vérifier si l'URL existe
        if (urlMapping.containsKey(path)) {
            // URL VALIDE → Afficher SEULEMENT cette URL
            showSingleMapping(request, response, path);
        } else {
            // URL INVALIDE → Afficher TOUTES les URLs (erreur 404 améliorée)
            showErrorPage(request, response, path);
        }
    }
    
    /**
     * Afficher UNIQUEMENT le mapping de l'URL demandée (URL valide)
     */
    private void showSingleMapping(HttpServletRequest request, HttpServletResponse response, String path) 
            throws ServletException, IOException {
        
        MethodInfo info = urlMapping.get(path);
        
        // Créer une Map avec UN SEUL élément
        Map<String, MethodInfo> singleMapping = new HashMap<>();
        singleMapping.put(path, info);
        
        request.setAttribute("urlMapping", singleMapping);
        request.setAttribute("totalUrls", 1);
        request.setAttribute("totalControllers", controllers.size());
        request.setAttribute("serverTime", new java.util.Date());
        request.setAttribute("currentUrl", path);
        request.setAttribute("isError", false);
        request.setAttribute("isSingle", true);  // Indicateur pour la JSP
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/mappings.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * Afficher TOUTES les URLs (URL invalide)
     */
    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String invalidUrl) 
            throws ServletException, IOException {
        
        request.setAttribute("urlMapping", urlMapping);
        request.setAttribute("totalUrls", urlMapping.size());
        request.setAttribute("totalControllers", controllers.size());
        request.setAttribute("serverTime", new java.util.Date());
        request.setAttribute("invalidUrl", invalidUrl);
        request.setAttribute("isError", true);
        request.setAttribute("isSingle", false);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/mappings.jsp");
        dispatcher.forward(request, response);
    }
}