package com.example.demo.user.repository;

import com.example.demo.user.domain.User;

import java.util.List;

public interface UserStore {
    String create(User newUser);
    void update(User newUser);
    void delete(String userid);

    //1먕의 유저 검색
    User retrieve(String userid);

    //모든 유저 검색
    List<User> retrieveAll();
}
