package mg.itu.framework;

import mg.itu.framework.Controller;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    public static List<Class<?>> scanControllers(ServletContext servletContext) throws Exception {
        List<Class<?>> controllers = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 1. Scan du dossier classes
        URL classesUrl = classLoader.getResource("");
        if (classesUrl != null) {
            String filePath = URLDecoder.decode(classesUrl.getFile(), "UTF-8");
            File classesDir = new File(filePath);
            if (classesDir.exists() && classesDir.isDirectory()) {
                scanDirectory(classLoader, classesDir, "", controllers);
            }
        }

        // 2. Scan du JAR principal (si applicable)
        String jarPath = ClassScanner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (jarPath.endsWith(".jar")) {
            scanJarFile(jarPath, controllers);
        }

        // 3. Scan des JARs dans WEB-INF/lib
        File libDir = new File(servletContext.getRealPath("/WEB-INF/lib"));
        if (libDir.exists() && libDir.isDirectory()) {
            File[] jars = libDir.listFiles();
            if (jars != null) {
                for (File jarFile : jars) {
                    if (jarFile.getName().endsWith(".jar") && !jarFile.getName().equals("servlet-api.jar")) {
                        scanJarFile(jarFile.getAbsolutePath(), controllers);
                    }
                }
            }
        }
        return controllers;
    }

    private static void scanDirectory(ClassLoader classLoader, File directory, String packageName, List<Class<?>> controllers) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                scanDirectory(classLoader, file, newPackage, controllers);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                if (className.contains("main.java")) continue;

                try {
                    Class<?> clazz = Class.forName(className, false, classLoader);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        controllers.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    // Ignorer
                }
            }
        }
    }

    private static void scanJarFile(String jarPath, List<Class<?>> controllers) throws Exception {
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.endsWith(".class") && !name.contains("module-info")) {
                    String className = name.replace("/", ".").replace(".class", "");
                    if (className.contains("main.java")) continue;

                    try {
                        Class<?> clazz = Class.forName(className, false, Thread.currentThread().getContextClassLoader());
                        if (clazz.isAnnotationPresent(Controller.class)) {
                            controllers.add(clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        // Ignorer
                    }
                }
            }
        }
    }
}