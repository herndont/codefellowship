package com.example.codefellowship.controllers;

import com.example.codefellowship.UserNotFoundException;
import com.example.codefellowship.database.ApplicationUser;
import com.example.codefellowship.database.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class UserAccountController {

    @Autowired
    ApplicationUserRepository userRepo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView postSignup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String dateOfBirth,
            @RequestParam String bio
    ) throws ParseException {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);

        Date dateObj=new SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirth);

        user.setDateOfBirth(dateObj);
        user.setBio(bio);

        userRepo.save(user);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(token);

        return new RedirectView("/");
    }

    // login

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/login-error")
    @ResponseBody
    public String getLoginError() {
        return "The Username or Password that you have used is incorrect";
    }

    @GetMapping ("/userInfo/{id}")
    public String getUserInfo(@PathVariable long id, Model model) {
        Optional<ApplicationUser> currentUser = userRepo.findById(id);
        if (currentUser.isPresent()) {
            model.addAttribute("userInfo", currentUser.get());
            return "userInfo";
        } else {
            throw new UserNotFoundException();
        }
    }

}
