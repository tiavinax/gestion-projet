package com.test.repository;

import mg.itu.framework.Repository;
import java.util.*;

@Repository
public class UserRepository {
    
    private final Map<Integer, String> users = new HashMap<>();
    
    public UserRepository() {
        users.put(1, "Alice");
        users.put(2, "Bob");
        users.put(3, "Charlie");
    }
    
    public List<String> findAll() {
        return new ArrayList<>(users.values());
    }
    
    public String findById(int id) {
        return users.get(id);
    }
    
    public void save(int id, String name) {
        users.put(id, name);
    }
}