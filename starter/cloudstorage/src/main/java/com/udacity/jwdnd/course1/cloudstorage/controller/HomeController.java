package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final CredentialService credentialService;
    private final UserService userService;

    public HomeController(UserService userService, CredentialService credentialService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping()
    public String loginView(Authentication authentication, Model model) {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("credentials", credentialService.getCredentials(userId));
        return "home";
    }
}
