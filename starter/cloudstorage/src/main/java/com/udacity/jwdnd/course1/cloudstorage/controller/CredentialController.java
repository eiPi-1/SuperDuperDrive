package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Controller
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService,
                                EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/credentials")
    public String addCredential(Authentication authentication, Credential credential, Model model) {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        credential.setUserId(userId);

        String encodedKey = "";

        if (credential.getCredentialId() != null){
            encodedKey = this.credentialService.getKeyByCredentialId(credential);
        }
        else{
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            encodedKey = Base64.getEncoder().encodeToString(key);
        }

        String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        int numInsertedRows = this.credentialService.addOrEditCredential(credential);

        if (numInsertedRows >= 0){
            model.addAttribute("credentialSuccess","A new credential was added successfully!");
        } else {
            model.addAttribute("credentialError","Error while adding a credential. Please, try again!");
        }

        List<Credential> allExistingCredentials = this.credentialService.getCredentials(userId);

        model.addAttribute("activeTab", "credentials");
        model.addAttribute("credentials", allExistingCredentials);
        model.addAttribute("encryptionService", encryptionService);

        return "redirect:/home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") int credentialId, Model model){
        credentialService.deleteCredential(credentialId);

        model.addAttribute("activeTab", "credentials");
        model.addAttribute("encryptionService", encryptionService);

        return "redirect:/home";
    }
}
