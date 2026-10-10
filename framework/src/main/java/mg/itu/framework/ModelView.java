package mg.itu.framework;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
    private String viewName;
    private Map<String, Object> data;
    
    public ModelView(String viewName) {
        this.viewName = viewName;
        this.data = new HashMap<>();
    }
    
    public ModelView addAttribute(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
    
    public String getViewName() {
        return viewName;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
}