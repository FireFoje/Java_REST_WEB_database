//package ru.kata.spring.boot_security.demo.dataloader;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.entity.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import javax.annotation.PostConstruct;
//import java.util.Collections;
//
//@Component
//public class UserDataLoader implements CommandLineRunner {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//    public UserDataLoader(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @PostConstruct
//    public void init() {
//        User user1 = new User();
//        user1.setName("user1");
//        user1.setLastName("user1");
//        user1.setEmail("user1@gmail.com");
//        user1.setRoles(Collections.singleton(roleService.getRoleByName("ROLE_ADMIN")));
//        user1.setPassword("user1");
//        userService.addUser(user1);
//
//        User user2 = new User();
//        user2.setName("user2");
//        user2.setLastName("user2");
//        user2.setEmail("user2@gmail.com");
//        user2.setRoles(Collections.singleton(roleService.getRoleByName("ROLE_USER")));
//        user2.setPassword("user2");
//        userService.addUser(user2);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//    }
//}
