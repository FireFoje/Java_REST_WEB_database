package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Transactional
public class AdminController {

    private final UserService userService;
    RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage(Authentication authentication, Model model, User user) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("user", user);
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/admin/add")
    public String showAddNewForm(Model model, @ModelAttribute("user") User user) {
//        model.addAttribute("user", user);
        model.addAttribute("userForm", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "add_user";
    }

    @PostMapping("/admin/new")
    public String addUser(@ModelAttribute("userForm") User userForm, @RequestParam("roles") List<Long> roleIds, BindingResult result) {
        if (result.hasErrors()) {
            return "add_user";
        }
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleService.getRoleById(roleId);
            roles.add(role);
        }
        userForm.setRoles(roles);
        userService.saveUser(userForm);
        return "redirect:/admin/users";
    }

    @GetMapping("/")
    public String showProfile(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            model.addAttribute("user", user);
            return "index";
        } else {
            return "login";
        }
    }

    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("userProfile", userService.findUserById(id));
        return "user";
    }

    @GetMapping("/admin/user/{id}")
    public String adminGetUserProfile(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("userByAdmin", userService.findUserById(id));
        return "user";
    }

    @GetMapping("/admin/users")
    public String userList(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.getAllRoles());
        return "users";
    }

    @GetMapping("/admin/edit/{id}")
    public String getUser(@PathVariable("id") long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.getAllRoles());
        model.addAttribute("userRoles", user.getRoles());

        return "users";
    }

    @PutMapping("/admin/update/{id}")
    @Transactional
    public String updateUpdate(@ModelAttribute("user") User user, @PathVariable("id") long id, @RequestParam("roles") List<Long> roleIds, BindingResult result, Model model) {
        model.addAttribute("roleList", roleService.getAllRoles());
        if (result.hasErrors()) {
            user.setId(id);
            return "users";
        }
        Set<Role> roles = new HashSet<>();
        for (Long rolesId : roleIds) {
            Role role = roleService.getRoleById(rolesId);
            roles.add(role);
        }

        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/users";
    }
}
