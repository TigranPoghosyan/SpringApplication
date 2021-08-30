package com.example.sweater.Greetings.controller;

import com.example.sweater.Greetings.domain.Role;
import com.example.sweater.Greetings.domain.User;
import com.example.sweater.Greetings.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(User user, Map<String,Object> model){
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null){
            model.put("message","User Exists");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}

