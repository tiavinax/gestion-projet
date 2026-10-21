package com.test.service;

import mg.itu.framework.Service;
import mg.itu.framework.Autowired;
import com.test.repository.UserRepository;
import com.test.model.User;
import java.util.*;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(int id) {
        return userRepository.findById(id);
    }
    
    public void save(User user) {
        userRepository.save(user);
    }
    
    public void update(User user) {
        userRepository.update(user);
    }
    
    public void delete(int id) {
        userRepository.delete(id);
    }
}