package com.example.kpopstore.controllers;

import org.springframework.ui.Model;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthWebController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html в templates
    }

    @GetMapping("/profile")
    public String profilePage(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        model.addAttribute("user", oauth2User);
        return "profile";
    }
}

