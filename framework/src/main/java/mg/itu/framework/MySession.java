package mg.itu.framework;

import jakarta.servlet.http.HttpSession;

public class MySession {
    private HttpSession httpSession;

    public MySession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    // --- METHODES PRINCIPALES ---
    
    public void set(String key, Object value) {
        httpSession.setAttribute(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = httpSession.getAttribute(key);
        if (value != null && type.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }

    // --- AUTRES METHODES UTILES ---
    
    public void remove(String key) {
        httpSession.removeAttribute(key);
    }

    public void invalidate() {
        httpSession.invalidate();
    }

    public String getId() {
        return httpSession.getId();
    }
    
    public boolean contains(String key) {
        return httpSession.getAttribute(key) != null;
    }
}