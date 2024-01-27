package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String listOfUsers(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin/allUsers";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/new";
    }

    @PostMapping("/new")
    public String addNewUserToDb(@ModelAttribute("user") @Valid User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String editUserFromDb(@ModelAttribute("user") @Valid User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        user.getRoles().clear();
        userService.delete(userService.getById(id));
        return "redirect:/admin";
    }

}
