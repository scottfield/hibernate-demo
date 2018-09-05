package com.jackie.hibernate.service;

import com.jackie.hibernate.model.User;

import java.util.List;

/**
 * Created by jackie on 5/13/2016.
 */
public interface UserService {
    void addUser(User user);

    User updateUser(User user);

    User findUserByUsername(String username);

    User findUserById(Long id);

    void deleteUsers();

    void batchCreateUser(List<User> users);

    void hqUpdateUser(User user);
}
