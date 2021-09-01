package com.example.sweater.Greetings.controller;

import com.example.sweater.Greetings.domain.Role;
import com.example.sweater.Greetings.domain.User;
import com.example.sweater.Greetings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        List<User> allUsers = userService.findAll();
        model.addAttribute("users", allUsers);
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam("userId") User user,
                           @RequestParam String username,
                           @RequestParam Map<String, String> form,
                           Model model) {
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }

}
