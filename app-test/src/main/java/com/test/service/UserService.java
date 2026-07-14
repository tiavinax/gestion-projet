package com.test.service;

import mg.itu.framework.Service;
import mg.itu.framework.Autowired;
import com.test.repository.UserRepository;
import java.util.*;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<String> getAllUsers() {
        return userRepository.findAll();
    }
    
    public String getUserById(int id) {
        return userRepository.findById(id);
    }
}