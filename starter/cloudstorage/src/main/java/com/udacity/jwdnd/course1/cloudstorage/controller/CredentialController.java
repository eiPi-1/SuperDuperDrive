package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    @Autowired
    EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllCredentials(Authentication authentication, Model model) {
        model.addAttribute("credentials", this.credentialService.getCredentials(
                this.userService.getUser(authentication.getName()).getUserId()));
        return "home";
    }

    @PostMapping("/credentials")
    public String addCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        credentialForm.setUserId(userId);

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        credentialForm.setCredentialKey(encryptionService.encryptValue(credentialForm.getPassword(), encodedKey));
        this.credentialService.addCredential(credentialForm);

        model.addAttribute("credentials", this.credentialService.getCredentials(userId));
        return "redirect:/home";
    }
}
