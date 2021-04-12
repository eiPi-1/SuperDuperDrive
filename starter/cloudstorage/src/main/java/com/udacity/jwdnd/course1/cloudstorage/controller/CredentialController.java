package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Controller
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService,
                                EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getAllCredentials(Authentication authentication, Model model) {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        List<Credential> allExistingCredentials = this.credentialService.getCredentials(userId);
        model.addAttribute("credentials", allExistingCredentials);

        return "redirect:/home";
    }

    @PostMapping("/credentials")
    public String addCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        credentialForm.setUserId(userId);

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        credentialForm.setKey(encodedKey);
        credentialForm.setPassword(encryptedPassword);
        int numInsertedRows = this.credentialService.addCredential(credentialForm);

        List<Credential> allExistingCredentials = this.credentialService.getCredentials(userId);

        //System.out.println("Added number of new credentials " + String.valueOf(numInsertedRows));
        //System.out.println("Fetching to display credentials " + String.valueOf(allExistingCredentials.size()));

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
