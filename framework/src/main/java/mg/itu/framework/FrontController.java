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
        this.hasMappingConflicts = (boolean) context.getAttribute("hasMappingConflicts");
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
        String httpMethod = request.getMethod();

        if (hasMappingConflicts) {
            request.setAttribute("mappingErrors", mappingErrors);
            request.setAttribute("invalidUrl", path);
            request.setAttribute("requestedMethod", httpMethod);
            render(request, response, "/WEB-INF/views/errors/erreur.jsp");
            return;
        }

        if (isHomePage(path)) {
            request.setAttribute("isError", false);
            render(request, response, "/WEB-INF/views/mappings.jsp");
            return;
        }

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

    private void handleNotFound(HttpServletRequest request, HttpServletResponse response, 
                                String path, String httpMethod) 
            throws ServletException, IOException {
        
        List<String> availableMethods = getAvailableMethods(path);
        
        request.setAttribute("invalidUrl", path);
        request.setAttribute("isError", true);
        request.setAttribute("isMethodError", !availableMethods.isEmpty());
        
        if (!availableMethods.isEmpty()) {
            request.setAttribute("requestedMethod", httpMethod);
            request.setAttribute("availableMethods", availableMethods);
        }
        
        render(request, response, "/WEB-INF/views/mappings.jsp");
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

            Object result = invokeMethod(controller, method, request);

            if (result instanceof String) {
                String viewName = (String) result;
                request.setAttribute("currentUrl", key.getUrl());
                request.setAttribute("currentMethod", key.getMethod());
                request.setAttribute("isError", false);
                request.setAttribute("isSingle", true);
                render(request, response, "/WEB-INF/views/" + viewName + ".jsp");
            } else {
                writeRawResponse(response, key, info);
            }

        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'execution du controleur", e);
        }
    }

    private Object invokeMethod(Object controller, Method method, HttpServletRequest request) 
            throws Exception {
        
        Class<?>[] paramTypes = method.getParameterTypes();
        
        if (paramTypes.length == 0) {
            return method.invoke(controller);
        } else if (paramTypes.length == 1 && paramTypes[0].equals(HttpServletRequest.class)) {
            return method.invoke(controller, request);
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