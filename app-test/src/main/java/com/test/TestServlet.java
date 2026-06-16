package com.test;

import com.test.framework.TestCommunication; 
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Tester la communication avec le framework
        TestCommunication testComm = new TestCommunication();
        
        out.println("<html>");
        out.println("<head><title>Test Framework</title></head>");
        out.println("<body>");
        out.println("<h1>🧪 Test de Communication Framework ↔ App Test</h1>");
        out.println("<hr/>");
        out.println("<h2>" + TestCommunication.getMessage() + "</h2>");
        out.println("<p>" + testComm.getFrameworkInfo() + "</p>");
        out.println("<hr/>");
        out.println("<h3>✅ COMMUNICATION RÉUSSIE !</h3>");
        out.println("<p>Le framework est correctement utilisé par l'application de test.</p>");
        out.println("</body>");
        out.println("</html>");
    }
}