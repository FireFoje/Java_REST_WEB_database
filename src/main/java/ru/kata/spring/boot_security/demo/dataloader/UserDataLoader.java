package ru.kata.spring.boot_security.demo.dataloader;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
@DependsOn("dataSource")
public class UserDataLoader {

    private final UserService userService;
    private final RoleService roleService;

    public UserDataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void postConstruct() {
        Role adminRole = new Role((long)1, "ROLE_ADMIN");
        Role userRole = new Role((long)2, "ROLE_USER");
        roleService.addRole(adminRole);
        roleService.addRole(userRole);

        User user1 = new User("user1", "user1", "user1", "user1@gmail.com", Set.of(adminRole));
        userService.addUser(user1);

        User user2 = new User("user2", "user2", "user2", "user2@gmail.com", Set.of(userRole));
        userService.addUser(user2);
    }
}