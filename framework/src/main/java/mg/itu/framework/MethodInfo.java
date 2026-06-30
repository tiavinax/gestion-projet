package mg.itu.framework;

import java.lang.reflect.Method;

public class MethodInfo {
    private Class<?> controllerClass;
    private Method method;
    
    public MethodInfo(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }
    
    public Class<?> getControllerClass() {
        return controllerClass;
    }
    
    public Method getMethod() {
        return method;
    }
    
    public String getControllerName() {
        return controllerClass.getSimpleName();
    }
    
    public String getMethodName() {
        return method.getName();
    }
    
    @Override
    public String toString() {
        return getControllerName() + "." + getMethodName() + "()";
    }
}