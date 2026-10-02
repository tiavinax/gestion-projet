package mg.itu.framework;

import com.google.gson.Gson;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FrontController extends HttpServlet {

    private List<Class<?>> controllers;
    private Map<UrlMethod, MethodInfo> urlMapping;
    private List<String> mappingErrors;
    private boolean hasMappingConflicts;
    private ApplicationContext applicationContext;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();

        // Récupérer le conteneur
        this.applicationContext = (ApplicationContext) context.getAttribute("applicationContext");

        this.controllers = (List<Class<?>>) context.getAttribute("controllers");
        this.urlMapping = (Map<UrlMethod, MethodInfo>) context.getAttribute("urlMapping");
        this.mappingErrors = (List<String>) context.getAttribute("mappingErrors");

        Boolean conflicts = (Boolean) context.getAttribute("hasMappingConflicts");
        this.hasMappingConflicts = conflicts != null && conflicts;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // 1. Conflits de mapping
        if (hasMappingConflicts) {
            request.setAttribute("mappingErrors", mappingErrors);
            request.setAttribute("invalidUrl", path);
            render(request, response, "/WEB-INF/views/errors/erreur.jsp");
            return;
        }

        // 2. Page d'accueil → route / vers SprintController.home()
        if (isHomePage(path)) {
            // Laisser le FrontController traiter la route /
            // Elle est dans urlMapping grâce à @RequestMapping("/")
        }

        // 3. Fichiers statiques
        if (isStaticResource(path)) {
            try {
                RequestDispatcher dispatcher = request.getRequestDispatcher(path);
                dispatcher.forward(request, response);
                return;
            } catch (Exception e) {
                // Ignorer
            }
        }

        // 4. Routes dynamiques
        String httpMethod = request.getMethod();
        UrlMethod key = new UrlMethod(path, httpMethod);

        if (urlMapping.containsKey(key)) {
            executeMethod(request, response, key);
        } else {
            handleNotFound(request, response, path, httpMethod);
        }
    }

    private boolean isHomePage(String path) {
        return path == null || "/".equals(path) || "".equals(path);
    }

    private boolean isStaticResource(String path) {
        return path.endsWith(".html") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".jpeg") ||
                path.endsWith(".gif") ||
                path.endsWith(".svg") ||
                path.endsWith(".ico") ||
                path.endsWith(".webp");
    }

    private void handleNotFound(HttpServletRequest request, HttpServletResponse response,
            String path, String httpMethod)
            throws ServletException, IOException {

        List<String> availableMethods = getAvailableMethods(path);

        request.setAttribute("invalidUrl", path);
        request.setAttribute("isError", true);
        request.setAttribute("isSingle", false);
        request.setAttribute("isMethodError", !availableMethods.isEmpty());

        if (!availableMethods.isEmpty()) {
            request.setAttribute("requestedMethod", httpMethod);
            request.setAttribute("availableMethods", availableMethods);
        }

        render(request, response, "/WEB-INF/views/errors/404.jsp");
    }

    private List<String> getAvailableMethods(String path) {
        List<String> methods = new ArrayList<>();
        for (UrlMethod key : urlMapping.keySet()) {
            if (key.getUrl().equals(path)) {
                methods.add(key.getMethod());
            }
        }
        return methods;
    }

     private void executeMethod(HttpServletRequest request, HttpServletResponse response, UrlMethod key)
            throws ServletException, IOException {

        try {
            MethodInfo info = urlMapping.get(key);
            Method method = info.getMethod();
            Class<?> controllerClass = info.getControllerClass();
            
            Object controller = applicationContext.getBean(controllerClass);
            if (controller == null) {
                controller = controllerClass.getDeclaredConstructor().newInstance();
            }

            Object result = invokeMethod(controller, method, request, response);

            // ============================================
            // SPRINT 6 : Détection @WebAPI
            // ============================================
            if (method.isAnnotationPresent(WebAPI.class)) {
                writeJsonResponse(response, result);
            } else if (result instanceof ModelView) {
                ModelView mv = (ModelView) result;
                
                for (Map.Entry<String, Object> entry : mv.getData().entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                
                String viewPath = resolveView(mv.getViewName());
                request.setAttribute("currentUrl", key.getUrl());
                request.setAttribute("currentMethod", key.getMethod());
                request.setAttribute("isError", false);
                request.setAttribute("isSingle", true);
                render(request, response, viewPath);
                
            } else if (result instanceof String) {
                String viewPath = resolveView((String) result);
                request.setAttribute("currentUrl", key.getUrl());
                request.setAttribute("currentMethod", key.getMethod());
                request.setAttribute("isError", false);
                request.setAttribute("isSingle", true);
                render(request, response, viewPath);
                
            } else {
                writeRawResponse(response, key, info);
            }

        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'execution du controleur", e);
        }
    }

    // ============================================
    // SPRINT 6 : Ecriture du JSON
    // ============================================
    private void writeJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String json = gson.toJson(data);
        
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

        private Object invokeMethod(Object controller, Method method,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Class<?>[] paramTypes = method.getParameterTypes();
        java.lang.reflect.Parameter[] parameters = method.getParameters();

        // Aucun paramètre
        if (paramTypes.length == 0) {
            return method.invoke(controller);
        }

        // Construire les arguments
        Object[] args = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> type = paramTypes[i];
            String paramName = parameters[i].getName();

            if (type.equals(HttpServletRequest.class)) {
                args[i] = request;
            } else if (type.equals(HttpServletResponse.class)) {
                args[i] = response;
            } else {
                // Binding automatique depuis les paramètres de la requête
                String value = request.getParameter(paramName);
                args[i] = convertValue(value, type);
            }
        }

        return method.invoke(controller, args);
    }

    /**
     * Convertit une valeur String vers le type cible
     */
    private Object convertValue(String value, Class<?> type) {
        if (value == null || value.isEmpty()) {
            return getDefaultValue(type);
        }

        if (type.equals(String.class)) {
            return value;
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return Long.parseLong(value);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return Float.parseFloat(value);
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (type.equals(java.util.Date.class)) {
            try {
                return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value);
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    /**
     * Retourne la valeur par défaut pour un type
     */
    private Object getDefaultValue(Class<?> type) {
        if (type.equals(int.class)) return 0;
        if (type.equals(long.class)) return 0L;
        if (type.equals(double.class)) return 0.0;
        if (type.equals(float.class)) return 0.0f;
        if (type.equals(boolean.class)) return false;
        return null;
    }

    private String resolveView(String viewName) {
        if (!viewName.startsWith("/")) {
            viewName = "/" + viewName;
        }
        if (!viewName.endsWith(".jsp")) {
            viewName = viewName + ".jsp";
        }
        return "/WEB-INF/views" + viewName;
    }

    private void writeRawResponse(HttpServletResponse response, UrlMethod key, MethodInfo info)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>Methode executee</h1>");
        out.println("<p>URL: " + key.getUrl() + " (" + key.getMethod() + ")</p>");
        out.println("<p>Controleur: " + info.getControllerName() + "</p>");
        out.println("<p>Methode: " + info.getMethodName() + "()</p>");
        out.println("<p><a href='" + getServletContext().getContextPath() + "/'>Retour</a></p>");
    }

    private void render(HttpServletRequest request, HttpServletResponse response, String viewPath)
            throws ServletException, IOException {

        request.setAttribute("urlMapping", urlMapping);
        request.setAttribute("totalUrls", urlMapping.size());
        request.setAttribute("totalControllers", controllers.size());
        request.setAttribute("serverTime", new java.util.Date());

        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}