package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/add")
    public String showAddNewForm(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "add_user";
    }

    @PostMapping("/new")
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
        model.addAttribute("user", userService.findUserById(id));
        return "user";
    }

    @GetMapping("/admin/user/{id}")
    public String adminGetUserProfile(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("userByAdmin", userService.findUserById(id));
        return "user";
    }

    @GetMapping("/admin/users")
    public String userList(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/admin/edit/{id}")
    public String getUser(@PathVariable("id") long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String updateUpdate(@PathVariable("id") long id, @Valid User user, @RequestParam("roles") List<Long> roleIds, BindingResult result) {
        if (result.hasErrors()) {
            user.setId(id);
            return "edit";
        }
        Set<Role> roles = new HashSet<>();
        for (Long rolesId : roleIds) {
            Role role = roleService.getRoleById(rolesId);
            roles.add(role);
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/";
    }

    @PostMapping("/admin/users")
    public String deleteUser(@RequestParam(defaultValue = "") Long userId,
                             @RequestParam(defaultValue = "") String action) {
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }
        return "redirect:/admin/users";
    }
}
