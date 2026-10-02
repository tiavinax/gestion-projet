package com.test.repository;

import mg.itu.framework.Repository;
import com.test.model.User;
import java.util.*;

@Repository
public class UserRepository {
    
    private final Map<Integer, User> users = new LinkedHashMap<>();
    
    public UserRepository() {
        users.put(1, new User(1, "Alice", "alice@mail.com"));
        users.put(2, new User(2, "Bob", "bob@mail.com"));
        users.put(3, new User(3, "Charlie", "charlie@mail.com"));
    }
    
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    public User findById(int id) {
        return users.get(id);
    }
    
    public void save(User user) {
        users.put(user.getId(), user);
    }
}