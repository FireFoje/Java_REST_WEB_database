package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String loginPage(Authentication authentication, Model model, User user) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("user", user);
            return "index";
        }
        return "login";
    }




    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }
    @GetMapping("/user/get/{id}")
    public String getUserProfile(@PathVariable(value = "id", required = true) long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin";
    }
    @GetMapping("/admin/get/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.findUserById(userId));
        return "admin";
    }
}
