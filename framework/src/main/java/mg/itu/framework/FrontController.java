package mg.itu.framework;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FrontController extends HttpServlet {
    
    private List<Class<?>> controllers = new ArrayList<>();
    
    @Override
    public void init() throws ServletException {
        System.out.println("========================================");
        System.out.println("🚀 FRAMEWORK MVC - DEMARRAGE");
        System.out.println("========================================");
        
        try {
            scanControllers();
            displayControllers();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors du scan des contrôleurs", e);
        }
    }
    
    private void scanControllers() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
        // MÉTHODE 1: Scanner le dossier WEB-INF/classes
        URL classesUrl = classLoader.getResource("");
        if (classesUrl != null) {
            String filePath = URLDecoder.decode(classesUrl.getFile(), "UTF-8");
            File classesDir = new File(filePath);
            if (classesDir.exists() && classesDir.isDirectory()) {
                System.out.println("📁 Scan du dossier: " + filePath);
                scanDirectory(classLoader, classesDir, "");
            }
        }
        
        // MÉTHODE 2: Scanner le JAR du framework
        String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if (jarPath.endsWith(".jar")) {
            System.out.println("📦 Scan du JAR: " + jarPath);
            scanJarFile(jarPath);
        }
    }
    
    private void scanDirectory(ClassLoader classLoader, File directory, String packageName) {
        File[] files = directory.listFiles();
        if (files == null) return;
        
        for (File file : files) {
            if (file.isDirectory()) {
                String newPackage = packageName + "." + file.getName();
                scanDirectory(classLoader, file, newPackage);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                // Nettoyer le nom de la classe (enlever le point initial)
                if (className.startsWith(".")) {
                    className = className.substring(1);
                }
                try {
                    Class<?> clazz = Class.forName(className, false, classLoader);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        controllers.add(clazz);
                        System.out.println("✅ Contrôleur trouvé: " + clazz.getName());
                    }
                } catch (ClassNotFoundException e) {
                    // Ignorer les classes qui ne peuvent pas être chargées
                }
            }
        }
    }
    
    private void scanJarFile(String jarPath) throws Exception {
        // Enlever le préfixe "file:" si présent
        if (jarPath.startsWith("file:")) {
            jarPath = jarPath.substring(5);
        }
        // Enlever les paramètres après "!"
        int paramIndex = jarPath.indexOf("!");
        if (paramIndex != -1) {
            jarPath = jarPath.substring(0, paramIndex);
        }
        
        try (java.util.jar.JarFile jarFile = new java.util.jar.JarFile(jarPath)) {
            Enumeration<java.util.jar.JarEntry> entries = jarFile.entries();
            
            while (entries.hasMoreElements()) {
                java.util.jar.JarEntry entry = entries.nextElement();
                String name = entry.getName();
                
                if (name.endsWith(".class") && !name.contains("module-info")) {
                    String className = name.replace("/", ".").replace(".class", "");
                    if (className.startsWith(".")) {
                        className = className.substring(1);
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
    
    private void displayControllers() {
        System.out.println("========================================");
        System.out.println("📋 LISTE DES CONTRÔLEURS TROUVÉS");
        System.out.println("========================================");
        System.out.println("Nombre total: " + controllers.size());
        
        if (controllers.isEmpty()) {
            System.out.println("⚠️ Aucun contrôleur trouvé !");
        } else {
            for (int i = 0; i < controllers.size(); i++) {
                Class<?> clazz = controllers.get(i);
                Controller annotation = clazz.getAnnotation(Controller.class);
                String name = annotation.value().isEmpty() ? clazz.getSimpleName() : annotation.value();
                System.out.println((i+1) + ". " + name + " → " + clazz.getName());
            }
        }
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
        System.out.println("🔍 Requête reçue: " + path);
        
        // Transmettre la liste à la JSP
        request.setAttribute("controllers", controllers);
        request.setAttribute("totalControllers", controllers.size());
        request.setAttribute("serverTime", new java.util.Date());
        
        // Rediriger vers la JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/liste_controller.jsp");
        dispatcher.forward(request, response);
    }
}