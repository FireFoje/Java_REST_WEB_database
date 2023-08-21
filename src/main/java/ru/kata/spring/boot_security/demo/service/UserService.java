package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);

    UserDetails loadUserByUsername(String username);

    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(Long userId);

    public void updateUser(User user);

}
