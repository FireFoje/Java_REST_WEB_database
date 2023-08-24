//package ru.kata.spring.boot_security.demo.dataloader;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import java.util.Collections;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//    public DataLoader(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (args.length > 0 && args[0].equals("create-drop")) {
//            Role role1 = new Role();
//            role1.setName("ROLE_ADMIN");
//            roleService.saveRole(role1);
//
//            Role role2 = new Role();
//            role2.setName("ROLE_USER");
//            roleService.saveRole(role2);
//
//            User user1 = new User();
//            user1.setName("user1");
//            user1.setLastName("user1");
//            user1.setEmail("user1@gmail.com");
//            user1.setRoles(Collections.singleton(roleService.getRoleByName("ROLE_ADMIN")));
//            user1.setPassword("user1");
//            userService.saveUser(user1);
//
//            User user2 = new User();
//            user2.setName("user2");
//            user2.setLastName("user2");
//            user2.setEmail("user2@gmail.com");
//            user2.setRoles(Collections.singleton(roleService.getRoleByName("ROLE_USER")));
//            user2.setPassword("user2");
//            userService.saveUser(user2);
//        }
//    }
//}
