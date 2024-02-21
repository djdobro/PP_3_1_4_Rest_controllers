package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping
    public String listOfUsers(Model model, Principal principal) {
        model.addAttribute("allUsers", userServiceImpl.allUsers());
        model.addAttribute("currentUser", userServiceImpl.findByUsername(principal.getName()));
        model.addAttribute("roles", roleServiceImpl.getAllRoles());
        return "admin/allUsers";
    }

    @GetMapping("/currentAdminUser")
    public String showCurrentUser(Model model, Principal principal) {
        model.addAttribute("currentAdminUser", userServiceImpl.findByUsername(principal.getName()));
        return "admin/currentAdminUser";
    }
}
