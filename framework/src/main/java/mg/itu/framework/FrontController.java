package mg.itu.framework;

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

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();

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
            Object controller = info.getControllerClass().getDeclaredConstructor().newInstance();

            Object result = invokeMethod(controller, method, request, response);

            if (result instanceof ModelView) {
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

    private Object invokeMethod(Object controller, Method method,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Class<?>[] paramTypes = method.getParameterTypes();

        if (paramTypes.length == 0) {
            return method.invoke(controller);
        } else if (paramTypes.length == 1 && paramTypes[0].equals(HttpServletRequest.class)) {
            return method.invoke(controller, request);
        } else if (paramTypes.length == 2 &&
                paramTypes[0].equals(HttpServletRequest.class) &&
                paramTypes[1].equals(HttpServletResponse.class)) {
            return method.invoke(controller, request, response);
        } else {
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                if (paramTypes[i].equals(HttpServletRequest.class)) {
                    args[i] = request;
                } else if (paramTypes[i].equals(HttpServletResponse.class)) {
                    args[i] = response;
                } else {
                    args[i] = null;
                }
            }
            return method.invoke(controller, args);
        }
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