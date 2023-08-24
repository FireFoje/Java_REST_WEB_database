package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;


public interface RoleService {
    Role getRoleById(Long id);
    Role getRoleByName(String name); //Используется в DataLoader
    List<Role> getAllRoles();
    void saveRole(Role role); //Используется в DataLoader

}
