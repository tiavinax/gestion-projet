package mg.itu.framework;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationContext {
    
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Map<String, Object> beansByName = new HashMap<>();
    private final List<Class<?>> beanClasses = new ArrayList<>();
    
    public ApplicationContext() {
    }
    
    public void registerBean(Class<?> clazz, Object instance) {
        beans.put(clazz, instance);
        beansByName.put(clazz.getSimpleName(), instance);
        beanClasses.add(clazz);
    }
    
    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
    
    public Object getBean(String name) {
        return beansByName.get(name);
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
        System.out.println("Total: " + beans.size() + " beans");
        System.out.println("========================================");
    }
}