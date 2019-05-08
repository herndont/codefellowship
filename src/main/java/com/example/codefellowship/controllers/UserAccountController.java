package com.example.codefellowship.controllers;

import com.example.codefellowship.database.ApplicationUser;
import com.example.codefellowship.database.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class UserAccountController {

    @Autowired
    ApplicationUserRepository repo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Date dateOfBirth,
            @RequestParam String bio
    ){
        ApplicationUser user = new ApplicationUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setBio(bio);

        repo.save(user);

        return "/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/login-error")
    @ResponseBody
    public String getLoginError() {
        return "The Username or Password that you have used is incorrect";
    }
}
