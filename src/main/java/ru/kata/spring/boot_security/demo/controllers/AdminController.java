package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleRepository roleRepository) {
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String listOfUsers(Model model, Principal principal) {
        model.addAttribute("allUsers", userServiceImpl.allUsers());
        model.addAttribute("currentUser", userServiceImpl.findByUsername(principal.getName()));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/allUsers";
    }

    @GetMapping("/currentAdminUser")
    public String showCurrentUser(Model model, Principal principal) {
        model.addAttribute("currentAdminUser", userServiceImpl.findByUsername(principal.getName()));
        return "admin/currentAdminUser";
    }

    @GetMapping("/newUser")
    public String showNewUser(@ModelAttribute("newUser") User user, Model model, Principal principal) {
        model.addAttribute("currentUser", userServiceImpl.findByUsername(principal.getName()));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/newUser";
    }

    @PostMapping()
    public String addUserToDb(@ModelAttribute("newUser") User user) {
        userServiceImpl.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(User user) {
        userServiceImpl.delete(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userServiceImpl.update(user);
        return "redirect:/admin";
    }
}
