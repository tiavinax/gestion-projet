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
    private Map<UrlMethod, MethodInfo> urlMapping = new HashMap<>();

    @Override
    public void init() throws ServletException {
        System.out.println("========================================");
        System.out.println("FRAMEWORK MVC - SPRINT 3");
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

        URL classesUrl = classLoader.getResource("");
        if (classesUrl != null) {
            String filePath = URLDecoder.decode(classesUrl.getFile(), "UTF-8");
            System.out.println("Scan: " + filePath);
            File classesDir = new File(filePath);
            if (classesDir.exists() && classesDir.isDirectory()) {
                scanDirectory(classLoader, classesDir, "");
            }
        }

        String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if (jarPath.endsWith(".jar")) {
            System.out.println("Scan JAR: " + jarPath);
            scanJarFile(jarPath);
        }

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
        if (files == null)
            return;

        for (File file : files) {
            if (file.isDirectory()) {
                String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                scanDirectory(classLoader, file, newPackage);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                if (className.contains("main.java")) {
                    continue;
                }
                try {
                    Class<?> clazz = Class.forName(className, false, classLoader);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        controllers.add(clazz);
                        System.out.println("Controleur trouve: " + clazz.getName());
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
                    if (className.contains("main.java")) {
                        continue;
                    }
                    try {
                        Class<?> clazz = Class.forName(className, false,
                                Thread.currentThread().getContextClassLoader());
                        if (clazz.isAnnotationPresent(Controller.class)) {
                            controllers.add(clazz);
                            System.out.println("Controleur trouve (JAR): " + clazz.getName());
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
        System.out.println("Scan des methodes @RequestMapping");
        System.out.println("========================================");

        for (Class<?> controllerClass : controllers) {
            Method[] methods = controllerClass.getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    String url = annotation.value().trim();
                    String httpMethod = annotation.method().trim().toUpperCase();

                    if (!url.startsWith("/")) {
                        url = "/" + url;
                    }

                    UrlMethod key = new UrlMethod(url, httpMethod);
                    MethodInfo info = new MethodInfo(controllerClass, method);
                    urlMapping.put(key, info);

                    System.out.println("Mapping: " + url + " (" + httpMethod + ") -> " +
                            controllerClass.getSimpleName() + "." + method.getName() + "()");
                }
            }
        }

        System.out.println("Total mappings: " + urlMapping.size());
        System.out.println("========================================");
    }

    private void displayMappings() {
        System.out.println("========================================");
        System.out.println("URLs supportees");
        System.out.println("========================================");

        if (urlMapping.isEmpty()) {
            System.out.println("Aucun mapping");
        } else {
            int i = 1;
            for (Map.Entry<UrlMethod, MethodInfo> entry : urlMapping.entrySet()) {
                UrlMethod key = entry.getKey();
                MethodInfo info = entry.getValue();
                System.out.println((i++) + ". " + key.getUrl() + " (" + key.getMethod() + ") -> " +
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
        String httpMethod = request.getMethod();

        System.out.println("Requete: " + path + " (" + httpMethod + ")");

        // Page d'accueil
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

        // Recherche avec UrlMethod
        UrlMethod key = new UrlMethod(path, httpMethod);

        if (urlMapping.containsKey(key)) {
            // URL + Methode HTTP trouvee
            executeMethod(request, response, key);
        } else {
            // Verifier si l'URL existe avec une autre methode
            List<String> availableMethods = new ArrayList<>();
            for (UrlMethod k : urlMapping.keySet()) {
                if (k.getUrl().equals(path)) {
                    availableMethods.add(k.getMethod());
                }
            }

            if (!availableMethods.isEmpty()) {
                // URL existe mais pas pour cette methode HTTP
                showMethodErrorPage(request, response, path, httpMethod, availableMethods);
            } else {
                // URL n'existe pas du tout
                showErrorPage(request, response, path);
            }
        }
    }

    private void executeMethod(HttpServletRequest request, HttpServletResponse response, UrlMethod key)
            throws ServletException, IOException {

        try {
            MethodInfo info = urlMapping.get(key);
            Class<?> controllerClass = info.getControllerClass();
            Method method = info.getMethod();

            Object controller = controllerClass.getDeclaredConstructor().newInstance();

            // Vérifier si la méthode accepte HttpServletRequest
            Object result;
            if (method.getParameterCount() > 0 &&
                    method.getParameterTypes()[0].equals(HttpServletRequest.class)) {
                // Appeler avec le paramètre request
                result = method.invoke(controller, request);
            } else {
                // Appeler sans paramètre
                result = method.invoke(controller);
            }

            if (result instanceof String) {
                String viewName = (String) result;

                Map<UrlMethod, MethodInfo> singleMapping = new HashMap<>();
                singleMapping.put(key, info);

                request.setAttribute("urlMapping", singleMapping);
                request.setAttribute("totalUrls", 1);
                request.setAttribute("totalControllers", controllers.size());
                request.setAttribute("serverTime", new java.util.Date());
                request.setAttribute("currentUrl", key.getUrl());
                request.setAttribute("currentMethod", key.getMethod());
                request.setAttribute("isError", false);
                request.setAttribute("isSingle", true);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp");
                dispatcher.forward(request, response);
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h1>Methode executee</h1>");
                out.println("<p>URL: " + key.getUrl() + " (" + key.getMethod() + ")</p>");
                out.println("<p>Controleur: " + info.getControllerName() + "</p>");
                out.println("<p>Methode: " + info.getMethodName() + "()</p>");
                out.println("<p><a href='" + request.getContextPath() + "/'>Retour</a></p>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de l'execution", e);
        }
    }

    private void showMethodErrorPage(HttpServletRequest request, HttpServletResponse response,
            String path, String requestedMethod, List<String> availableMethods)
            throws ServletException, IOException {

        request.setAttribute("urlMapping", urlMapping);
        request.setAttribute("totalUrls", urlMapping.size());
        request.setAttribute("totalControllers", controllers.size());
        request.setAttribute("serverTime", new java.util.Date());
        request.setAttribute("invalidUrl", path);
        request.setAttribute("requestedMethod", requestedMethod);
        request.setAttribute("availableMethods", availableMethods);
        request.setAttribute("isError", true);
        request.setAttribute("isMethodError", true);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/mappings.jsp");
        dispatcher.forward(request, response);
    }

    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String invalidUrl)
            throws ServletException, IOException {

        request.setAttribute("urlMapping", urlMapping);
        request.setAttribute("totalUrls", urlMapping.size());
        request.setAttribute("totalControllers", controllers.size());
        request.setAttribute("serverTime", new java.util.Date());
        request.setAttribute("invalidUrl", invalidUrl);
        request.setAttribute("isError", true);
        request.setAttribute("isMethodError", false);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/mappings.jsp");
        dispatcher.forward(request, response);
    }
}