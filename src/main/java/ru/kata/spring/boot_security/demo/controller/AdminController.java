package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@Transactional
public class AdminController {
    @Autowired
    private UserService userService;


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
        return "add_user";
    }

    @PostMapping("/new")
    public String addUser(@Valid User userForm, BindingResult result) {
        if (result.hasErrors()) {
            return "add_user";
        }
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
        return "edit";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String updateUpdate(@PathVariable("id") long id, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            user.setId(id);
            return "edit";
        }
        userService.saveUser(user);
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
