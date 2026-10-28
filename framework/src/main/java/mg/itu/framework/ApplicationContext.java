package mg.itu.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext {
    
    // 1. Les classes scannées au démarrage (Eager register)
    private final List<Class<?>> beanClasses = new ArrayList<>();
    
    // 2. Les instances créées à la demande (Lazy load)
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Map<String, Object> beansByName = new HashMap<>();
    
    public void registerBeanClass(Class<?> clazz) {
        beanClasses.add(clazz);
    }
    
    public <T> T getBean(Class<T> clazz) {
        // Si l'instance existe déjà en mémoire, on la retourne (Singleton)
        if (beans.containsKey(clazz)) {
            return (T) beans.get(clazz);
        }
        
        // Si elle n'existe pas, on l'instancie (Lazy Loading)
        if (beanClasses.contains(clazz)) {
            try {
                Object instance = instantiateBean(clazz);
                
                // On sauvegarde l'instance immédiatement
                beans.put(clazz, instance);
                beansByName.put(clazz.getSimpleName(), instance);
                
                // On injecte les dépendances (@Autowired)
                injectDependencies(instance);
                
                return (T) instance;
            } catch (Exception e) {
                throw new RuntimeException("Erreur de création du bean : " + clazz.getName(), e);
            }
        }
        
        return null;
    }
    
    public Object getBean(String name) {
        return beansByName.get(name);
    }
    
    private Object instantiateBean(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
    
    private void injectDependencies(Object instance) throws Exception {
        Class<?> clazz = instance.getClass();
        
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                
                // Appel récursif : si la dépendance n'existe pas, getBean() va la créer
                Object dependency = getBean(field.getType());
                
                if (dependency != null) {
                    field.set(instance, dependency);
                }
            }
        }
    }
    
    public Map<Class<?>, Object> getBeans() {
        return beans;
    }
    
    public List<Class<?>> getBeanClasses() {
        return beanClasses;
    }
    
    public void showBeans() {
        System.out.println("========================================");
        System.out.println("BEANS DU CONTENEUR");
        System.out.println("========================================");
        int i = 1;
        for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
            System.out.println((i++) + ". " + entry.getKey().getSimpleName() + " → " + entry.getValue().getClass().getName());
        }
        System.out.println("Total: " + beans.size() + " beans instanciés (Lazy)");
        System.out.println("========================================");
    }
}