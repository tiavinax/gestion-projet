package mg.itu.framework;

import jakarta.servlet.ServletContext;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    // Seul le jar du framework est scanné dans les libs, filtré par ce préfixe
    private static final String FRAMEWORK_PACKAGE = "mg.itu.framework.";

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
            Class<? extends java.lang.annotation.Annotation> annotation) throws Exception {
        List<Class<?>> result = new ArrayList<>();
        for (Class<?> clazz : scanAllClasses(servletContext)) {
            if (clazz.isAnnotationPresent(annotation)) {
                result.add(clazz);
            }
        }
        return result;
    }

    public static List<Class<?>> scanAllClasses(ServletContext servletContext) throws Exception {
        // LinkedHashSet : pas de doublons, ordre conservé
        Set<Class<?>> classes = new LinkedHashSet<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 1. Code de l'application : WEB-INF/classes
        URL classesUrl = classLoader.getResource("");
        if (classesUrl != null) {
            String filePath = URLDecoder.decode(classesUrl.getFile(), "UTF-8");
            File classesDir = new File(filePath);
            if (classesDir.exists() && classesDir.isDirectory()) {
                scanDirectory(classLoader, classesDir, "", classes);
            }
        }

        // 2. Jar du framework uniquement, filtré par package
        String jarPath = ClassScanner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        jarPath = URLDecoder.decode(jarPath, "UTF-8");
        if (jarPath.endsWith(".jar")) {
            scanJarFile(jarPath, classLoader, classes);
        }

        // 3. Les autres jars (postgresql, gson...) ne sont PAS scannés
        return new ArrayList<>(classes);
    }

    private static void scanDirectory(ClassLoader classLoader, File directory,
                                      String packageName, Set<Class<?>> classes) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                scanDirectory(classLoader, file, newPackage, classes);
            } else if (file.getName().endsWith(".class")) {
                String simpleName = file.getName().replace(".class", "");
                String className = packageName.isEmpty() ? simpleName : packageName + "." + simpleName;
                if (className.contains("main.java")) continue;

                try {
                    classes.add(Class.forName(className, false, classLoader));
                } catch (ClassNotFoundException | LinkageError e) {
                    // classe inutilisable (dépendance absente) : on l'ignore
                }
            }
        }
    }

    private static void scanJarFile(String jarPath, ClassLoader classLoader,
                                    Set<Class<?>> classes) throws Exception {
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();

                if (name.endsWith(".class") && !name.contains("module-info")) {
                    String className = name.replace("/", ".").replace(".class", "");
                    if (!className.startsWith(FRAMEWORK_PACKAGE)) continue;

                    try {
                        classes.add(Class.forName(className, false, classLoader));
                    } catch (ClassNotFoundException | LinkageError e) {
                        // ignoré
                    }
                }
            }
        }
    }
}