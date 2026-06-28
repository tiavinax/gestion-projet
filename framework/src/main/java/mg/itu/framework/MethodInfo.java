package mg.itu.framework;

import java.lang.reflect.Method;

public class MethodInfo {
    private Class<?> controllerClass;
    private Method method;
    private String url;
    
    public MethodInfo(Class<?> controllerClass, Method method, String url) {
        this.controllerClass = controllerClass;
        this.method = method;
        this.url = url;
    }
    
    public Class<?> getControllerClass() {
        return controllerClass;
    }
    
    public Method getMethod() {
        return method;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getControllerName() {
        return controllerClass.getSimpleName();
    }
    
    public String getMethodName() {
        return method.getName();
    }
    
    @Override
    public String toString() {
        return url + " → " + getControllerName() + "." + getMethodName() + "()";
    }
}