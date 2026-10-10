package mg.itu.framework;

import jakarta.servlet.ServletContext;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    public static List<Class<?>> scanControllers(ServletContext servletContext) throws Exception {
        return scanClassesWithAnnotation(servletContext, Controller.class);
    }

    public static List<Class<?>> scanComponents(ServletContext servletContext) throws Exception {
        List<Class<?>> components = new ArrayList<>();
        List<Class<?>> allClasses = scanAllClasses(servletContext);
        
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(Component.class) ||
                clazz.isAnnotationPresent(Controller.class) ||
                clazz.isAnnotationPresent(Repository.class) ||
                clazz.isAnnotationPresent(Service.class)) {
                components.add(clazz);
            }
        }
        return components;
    }

    public static List<Class<?>> scanClassesWithAnnotation(ServletContext servletContext, 
                                                            Class<? extends java.lang.annotation.Annotation> annotation) 
            throws Exception {
        List<Class<?>> result = new ArrayList<>();
        List<Class<?>> allClasses = scanAllClasses(servletContext);
        
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(annotation)) {
                result.add(clazz);
            }
        }
        return result;
    }

    public static List<Class<?>> scanAllClasses(ServletContext servletContext) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 1. Scan du dossier classes
        URL classesUrl = classLoader.getResource("");
        if (classesUrl != null) {
            String filePath = URLDecoder.decode(classesUrl.getFile(), "UTF-8");
            File classesDir = new File(filePath);
            if (classesDir.exists() && classesDir.isDirectory()) {
                scanDirectory(classLoader, classesDir, "", classes);
            }
        }

        // 2. Scan du JAR principal
        String jarPath = ClassScanner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (jarPath.endsWith(".jar")) {
            scanJarFile(jarPath, classes);
        }

        // 3. Scan des JARs dans WEB-INF/lib
        File libDir = new File(servletContext.getRealPath("/WEB-INF/lib"));
        if (libDir.exists() && libDir.isDirectory()) {
            File[] jars = libDir.listFiles();
            if (jars != null) {
                for (File jarFile : jars) {
                    if (jarFile.getName().endsWith(".jar") && !jarFile.getName().equals("servlet-api.jar")) {
                        scanJarFile(jarFile.getAbsolutePath(), classes);
                    }
                }
            }
        }
        return classes;
    }

    private static void scanDirectory(ClassLoader classLoader, File directory, String packageName, List<Class<?>> classes) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                scanDirectory(classLoader, file, newPackage, classes);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                if (className.contains("main.java")) continue;

                try {
                    Class<?> clazz = Class.forName(className, false, classLoader);
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    // Ignorer
                }
            }
        }
    }

    private static void scanJarFile(String jarPath, List<Class<?>> classes) throws Exception {
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
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        // Ignorer
                    }
                }
            }
        }
    }
}