package com.example.demo.user.service;

import com.example.demo.user.domain.User;

import java.util.List;

public interface UserService2 {
    String register(User newUser);
    void modify(User newUser);
    void remove(String id);

    User find(String id);

    List<User> findAll();
}